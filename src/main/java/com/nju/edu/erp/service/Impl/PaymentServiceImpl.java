package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.enums.sheetState.PaymentState;
import com.nju.edu.erp.model.po.PaymentPO;
import com.nju.edu.erp.model.vo.Payment.PaymentVO;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.service.SheetService;

import java.util.List;

public class PaymentServiceImpl implements SheetService<PaymentPO, PaymentVO, PaymentState> {
    /**
     * 制定单
     *
     * @param userVO  用户VO
     * @param sheetVO 单VO
     */
    @Override
    public void makeSheet(UserVO userVO, PaymentVO sheetVO) {

    }

    /**
     * 根据状态获取单(state == null 则获取所有单)
     *
     * @param paymentState 单状态
     * @return 单
     */
    @Override
    public List<PaymentVO> getSheetByState(PaymentState paymentState) {
        return null;
    }

    /**
     * 根据单id进行审批(state), 注意，有的有两层审批，有的只要总经理审批
     * 在controller层进行权限控制
     *
     * @param sheetId      单id
     * @param paymentState 单修改后的状态
     */
    @Override
    public void approval(String sheetId, PaymentState paymentState) {

    }

    /**
     * 根据单Id搜索进货单信息
     *
     * @param sheetId 单Id
     * @return 单全部信息
     */
    @Override
    public PaymentVO getSheetById(String sheetId) {
        return null;
    }
}
