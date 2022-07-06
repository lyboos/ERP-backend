package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.enums.sheetState.ReceiptState;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.receipt.ReceiptVO;
import com.nju.edu.erp.service.ReceiptService;

import java.util.List;

public class ReceiptServiceImpl implements ReceiptService {
    /**
     * 制定单
     *
     * @param userVO  用户VO
     * @param sheetVO 单VO
     */
    @Override
    public void makeSheet(UserVO userVO, ReceiptVO sheetVO) {

    }

    /**
     * 根据状态获取单(state == null 则获取所有单)
     *
     * @param receiptState 单状态
     * @return 单
     */
    @Override
    public List<ReceiptVO> getSheetByState(ReceiptState receiptState) {
        return null;
    }

    /**
     * 根据单id进行审批(state), 注意，有的有两层审批，有的只要总经理审批
     * 在controller层进行权限控制
     *
     * @param sheetId      单id
     * @param receiptState 单修改后的状态
     */
    @Override
    public void approval(String sheetId, ReceiptState receiptState) {

    }

    /**
     * 根据单Id搜索进货单信息
     *
     * @param sheetId 单Id
     * @return 单全部信息
     */
    @Override
    public ReceiptVO getSheetById(String sheetId) {
        return null;
    }
}
