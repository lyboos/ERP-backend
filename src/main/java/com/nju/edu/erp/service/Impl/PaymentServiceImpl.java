package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.PaymentDao;
import com.nju.edu.erp.enums.sheetState.PaymentState;
import com.nju.edu.erp.model.po.CustomerPO;
import com.nju.edu.erp.model.po.PaymentContentPO;
import com.nju.edu.erp.model.po.PaymentPO;
import com.nju.edu.erp.model.vo.Payment.PaymentContentVO;
import com.nju.edu.erp.model.vo.Payment.PaymentVO;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.service.CustomerService;
import com.nju.edu.erp.service.PaymentService;
import com.nju.edu.erp.utils.IdGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    PaymentDao paymentDao;

    CustomerService customerService;

    @Autowired
    public PaymentServiceImpl(PaymentDao paymentDao, CustomerService customerService) {
        this.paymentDao = paymentDao;
        this.customerService = customerService;
    }

    /**
     * 制定单
     *
     * @param userVO  用户VO
     * @param sheetVO 单VO
     */
    @Override
    @Transactional
    public void makeSheet(UserVO userVO, PaymentVO sheetVO) {
        PaymentPO paymentPO = new PaymentPO();
        BeanUtils.copyProperties(sheetVO, paymentPO);

        paymentPO.setOperator(userVO.getName());
        paymentPO.setCreateTime(new Date());
        PaymentPO latest = paymentDao.getLatest();
        String id = IdGenerator.generateSheetId(latest == null? null : latest.getId(), "SKD");
        paymentPO.setId(id);
        paymentPO.setState(PaymentState.PENDING_LEVEL_2);
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<PaymentContentPO> paymentContentPOList = new ArrayList<>();
        for (PaymentContentVO contentVO: sheetVO.getSheetContent()) {
            PaymentContentPO contentPO = new PaymentContentPO();
            BeanUtils.copyProperties(contentVO, contentPO);
            contentPO.setSheetId(id);
            totalAmount = totalAmount.add(contentPO.getAmount());
            paymentContentPOList.add(contentPO);
        }
        paymentPO.setTotalAmount(totalAmount);
        paymentDao.save(paymentPO);
        paymentDao.saveBatch(paymentContentPOList);
    }

    /**
     * 根据状态获取单(state == null 则获取所有单)
     *
     * @param state 单状态
     * @return 单
     */
    @Override
    public List<PaymentVO> getSheetByState(PaymentState state) {
        List<PaymentVO> res = new ArrayList<>();
        List<PaymentPO> all;
        if (state == null) {
            all = paymentDao.findAll();
        } else {
            all = paymentDao.findAllByState(state);
        }
        for (PaymentPO po: all) {
            PaymentVO vo = new PaymentVO();
            BeanUtils.copyProperties(po, vo);
            List<PaymentContentPO> alll = paymentDao.findContentBySheetId(po.getId());
            copyContentFromPOContent(vo, alll);
            res.add(vo);
        }
        return res;
    }

    private void copyContentFromPOContent(PaymentVO vo, List<PaymentContentPO> list) {
        List<PaymentContentVO> vos = new ArrayList<>();
        for (PaymentContentPO p: list) {
            PaymentContentVO v = new PaymentContentVO();
            BeanUtils.copyProperties(p, v);
            vos.add(v);
        }
        vo.setSheetContent(vos);
    }

    /**
     * 根据单id进行审批(state), 注意，有的有两层审批，有的只要总经理审批
     * 在controller层进行权限控制
     *
     * @param sheetId      单id
     * @param state 单修改后的状态
     */
    @Override
    @Transactional
    public void approval(String sheetId, PaymentState state) {
        if (state.equals(PaymentState.FAILURE)) {
            PaymentPO sheet = paymentDao.findSheetById(sheetId);
            if (sheet.getState().equals(PaymentState.SUCCESS)) throw new RuntimeException("状态更新失败");
            int effectLines = paymentDao.updateState(sheetId, state);
            if (effectLines == 0) throw new RuntimeException("状态更新失败");
        } else {
            PaymentState prevState;
            if (state.equals(PaymentState.SUCCESS)) {
                prevState = PaymentState.PENDING_LEVEL_2;
            } else {
                throw new RuntimeException("状态更新失败");
            }
            int effectLines = paymentDao.updateStateV2(sheetId, prevState, state);
            if (effectLines == 0) throw new RuntimeException("状态更新失败");
            if (state.equals(PaymentState.SUCCESS)) {
                // 更新客户表的应付字段payable
                PaymentPO paymentPO = paymentDao.findSheetById(sheetId);
                CustomerPO customerPO = customerService.findCustomerById(paymentPO.getSupplier());
                customerPO.setPayable(customerPO.getPayable().subtract(paymentPO.getTotalAmount()));
                customerService.updateCustomer(customerPO);
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
    public PaymentVO getSheetById(String sheetId) {
        PaymentPO paymentPO = paymentDao.findSheetById(sheetId);
        if (paymentPO == null) return null;
        List<PaymentContentPO> contentPO = paymentDao.findContentBySheetId(sheetId);
        PaymentVO vo = new PaymentVO();
        BeanUtils.copyProperties(vo, contentPO);
        return vo;
    }
}
