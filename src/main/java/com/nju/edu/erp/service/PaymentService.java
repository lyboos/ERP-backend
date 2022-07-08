package com.nju.edu.erp.service;

import com.nju.edu.erp.enums.sheetState.PaymentState;
import com.nju.edu.erp.model.po.PaymentPO;
import com.nju.edu.erp.model.vo.Payment.PaymentVO;
import com.nju.edu.erp.model.vo.UserVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PaymentService extends SheetService<PaymentVO, PaymentState> {
    /**
     * 制定单
     *
     * @param userVO  用户VO
     * @param sheetVO 单VO
     */
    @Override
    void makeSheet(UserVO userVO, PaymentVO sheetVO);

    /**
     * 根据状态获取单(state == null 则获取所有单)
     *
     * @param state 单状态
     * @return 单
     */
    @Override
    List<PaymentVO> getSheetByState(PaymentState state);

    /**
     * 根据单id进行审批(state), 注意，有的有两层审批，有的只要总经理审批
     * 在controller层进行权限控制
     *
     * @param sheetId      单id
     * @param state 单修改后的状态
     */
    @Override
    void approval(String sheetId, PaymentState state);

    /**
     * 根据单Id搜索进货单信息
     *
     * @param sheetId 单Id
     * @return 单全部信息
     */
    @Override
    PaymentVO getSheetById(String sheetId);
}
