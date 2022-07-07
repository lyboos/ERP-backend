package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.enums.sheetState.SalarySheetState;
import com.nju.edu.erp.model.vo.SalarySheetVO;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.service.SalarySheetService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

// 面向接口编程。详见父接口
@Service
public class SalarySheetServiceImpl implements SalarySheetService {


    /**
     * 制定单
     *
     * @param userVO  用户VO
     * @param sheetVO 单VO
     */
    @Override
    public void makeSheet(UserVO userVO, SalarySheetVO sheetVO) {

    }

    /**
     * 根据状态获取单(state == null 则获取所有单)
     *
     * @param salarySheetState 单状态
     * @return 单
     */
    @Override
    public List<SalarySheetVO> getSheetByState(SalarySheetState salarySheetState) {
        return null;
    }

    /**
     * 根据单id进行审批(state), 注意，有的有两层审批，有的只要总经理审批
     * 在controller层进行权限控制
     *
     * @param sheetId          单id
     * @param salarySheetState 单修改后的状态
     */
    @Override
    public void approval(String sheetId, SalarySheetState salarySheetState) {

    }

    /**
     * 根据单Id搜索进货单信息
     *
     * @param sheetId 单Id
     * @return 单全部信息
     */
    @Override
    public SalarySheetVO getSheetById(String sheetId) {
        return null;
    }

    /**
     *
     * @param rawSalary (monthly, equals to yearly / 12)
     */
    private static BigDecimal calTaxes(BigDecimal rawSalary) {
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
