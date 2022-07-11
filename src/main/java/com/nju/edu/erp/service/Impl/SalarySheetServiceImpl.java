package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.SalarySheetDao;
import com.nju.edu.erp.dao.StaffDao;
import com.nju.edu.erp.enums.sheetState.SalarySheetState;
import com.nju.edu.erp.enums.strategy.PaymentRelevantInfo;
import com.nju.edu.erp.model.po.SalarySheetPO;
import com.nju.edu.erp.model.po.StaffInfoPO;
import com.nju.edu.erp.model.vo.SalarySheetVO;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.service.CheckInService;
import com.nju.edu.erp.service.SalarySheetService;
import com.nju.edu.erp.utils.IdGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

// 面向接口编程。详见父接口
@Service
public class SalarySheetServiceImpl implements SalarySheetService {

    SalarySheetDao salarySheetDao;

    StaffDao staffDao;

    CheckInService checkInService;

    @Autowired
    public SalarySheetServiceImpl(SalarySheetDao salarySheetDao, StaffDao staffDao, CheckInService checkInService) {
        this.salarySheetDao = salarySheetDao;
        this.staffDao = staffDao;
        this.checkInService = checkInService;
    }

    /**
     * 制定单
     *
     * @param userVO  用户VO
     * @param sheetVO 单VO
     */
    @Override
    public void makeSheet(UserVO userVO, SalarySheetVO sheetVO) {
        SalarySheetPO sheetPO = new SalarySheetPO();
        BeanUtils.copyProperties(sheetVO, sheetPO);

        // 如果是年终奖，计入12月
        sheetPO.setCreateTime(new Date());
        if (sheetVO.getIsBonus()) {
            sheetPO.getCreateTime().setMonth(Calendar.DECEMBER);
        }

        SalarySheetPO latest = salarySheetDao.getLatest();
        String id = IdGenerator.generateSheetId(latest == null? null : latest.getId(), "SKD");
        sheetPO.setId(id);
        StaffInfoPO staffInfoPO = staffDao.getStaffByName(sheetVO.getName());
        sheetPO.setSalaryAccount(staffInfoPO.getBankAccount());

        // 防御式编程
        if (!(sheetPO.getIsBonus() || staffInfoPO.getPaymentScheduleStrategy().isPayday()))
            throw new RuntimeException("不在发薪日或此单不是奖金，无法制作此单");

        sheetPO.setState(SalarySheetState.PENDING_LEVEL_2);
        // optional: 如果应发工资有填东西，就用填的，否则else自动计算
        if (sheetVO.getRawSalary() != null) {
            sheetPO.setRawSalary(sheetVO.getRawSalary());
        } else {
            PaymentRelevantInfo p = new PaymentRelevantInfo(staffInfoPO);
            BigDecimal rawSalary = staffInfoPO.getPaymentCalculatingStrategy().getAmount(p);
            int times = checkInService.checkInCount(sheetVO.getName());
            rawSalary = rawSalary.multiply(BigDecimal.valueOf(times));
            rawSalary = rawSalary.divide(BigDecimal.valueOf(30), rawSalary.scale() - BigDecimal.valueOf(30).scale());
            sheetPO.setRawSalary(rawSalary);
        }

        BigDecimal taxes = calTaxes(sheetPO.getRawSalary());
        sheetPO.setTaxes(taxes);
        sheetPO.setFinalSalary(sheetPO.getRawSalary().subtract(taxes));

        salarySheetDao.save(sheetPO);
    }

    /**
     * 根据状态获取单(state == null 则获取所有单)
     *
     * @param state 单状态
     * @return 单
     */
    @Override
    public List<SalarySheetVO> getSheetByState(SalarySheetState state) {
        List<SalarySheetVO> res = new ArrayList<>();
        List<SalarySheetPO> all;
        if (state == null) {
            all = salarySheetDao.findAll();
        } else {
            all = salarySheetDao.findAllByState(state);
        }
        for (SalarySheetPO po: all) {
            SalarySheetVO vo = new SalarySheetVO();
            BeanUtils.copyProperties(po, vo);
            res.add(vo);
        }
        return res;
    }

    /**
     * 根据单id进行审批(state), 注意，有的有两层审批，有的只要总经理审批
     * 在controller层进行权限控制
     *
     * @param sheetId          单id
     * @param state 单修改后的状态
     */
    @Override
    public void approval(String sheetId, SalarySheetState state) {
        if (state.equals(SalarySheetState.FAILURE)) {
            SalarySheetPO sheet = salarySheetDao.findSheetById(sheetId);
            if (sheet.getState().equals(SalarySheetState.SUCCESS)) throw new RuntimeException("状态更新失败");
            int effectLines = salarySheetDao.updateState(sheetId, state);
            if (effectLines == 0) throw new RuntimeException("状态更新失败");
        } else {
            SalarySheetState prevState;
            if (state.equals(SalarySheetState.SUCCESS)) {
                prevState = SalarySheetState.PENDING_LEVEL_2;
            } else {
                throw new RuntimeException("状态更新失败");
            }
            int effectLines = salarySheetDao.updateStateV2(sheetId, prevState, state);
            if (effectLines == 0) throw new RuntimeException("状态更新失败");
            if (state.equals(SalarySheetState.SUCCESS)) {
                // TODO: 通知财务人员
            }
        }
    }

    /**
     * 根据单Id搜索进货单信息
     *
     * @param sheetId 单Id
     * @return 单全部信息
     */
    @Override
    public SalarySheetVO getSheetById(String sheetId) {
        SalarySheetPO sheetPO = salarySheetDao.findSheetById(sheetId);
        if (sheetPO == null) return null;

        SalarySheetVO sheetVO = new SalarySheetVO();
        BeanUtils.copyProperties(sheetPO, sheetVO);
        return sheetVO;
    }

    /**
     *
     * @param rawSalary (monthly, equals to yearly / 12)
     */
    public static BigDecimal calTaxes(BigDecimal rawSalary) {
        // Code03
        // 失业保险为0.5%
        // 住房公积金7%~12%，按10%计算
        BigDecimal taxesSum = rawSalary.multiply(BigDecimal.valueOf(0.105));

        rawSalary = rawSalary.subtract(taxesSum);

        // 5000免征额
        rawSalary = rawSalary.subtract(BigDecimal.valueOf(5000));

        // 防御式编程
        if (rawSalary.compareTo(BigDecimal.ZERO) < 0) return taxesSum;

        // 表驱动计算税款
        BigDecimal[] bracket = {
                BigDecimal.valueOf(0),
                BigDecimal.valueOf(36000),
                BigDecimal.valueOf(144000),
                BigDecimal.valueOf(300000),
                BigDecimal.valueOf(420000),
                BigDecimal.valueOf(660000),
                BigDecimal.valueOf(960000),
        };
        BigDecimal[] percent = {
                BigDecimal.valueOf(0.03),
                BigDecimal.valueOf(0.10),
                BigDecimal.valueOf(0.20),
                BigDecimal.valueOf(0.25),
                BigDecimal.valueOf(0.30),
                BigDecimal.valueOf(0.35),
                BigDecimal.valueOf(0.45),
        };
        BigDecimal[] base = new BigDecimal[bracket.length];
        base[0] = BigDecimal.ZERO;
        for (int i = 1; i < base.length; i++) {
            base[i] = base[i - 1].add((bracket[i].subtract(bracket[i - 1])).multiply(percent[i - 1]));
        }

        int level = 0;
        for (int i = 1; i < base.length; i++) {
            if (rawSalary.compareTo(bracket[i]) > 0) {
                level++;
            }
        }
        BigDecimal tax;
        tax = base[level].add(percent[level].multiply(rawSalary.subtract(bracket[level])));
        taxesSum = taxesSum.add(tax);

        return taxesSum;
    }
}
