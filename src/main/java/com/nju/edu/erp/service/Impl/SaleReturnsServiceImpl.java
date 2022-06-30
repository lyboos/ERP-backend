package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.enums.sheetState.PurchaseReturnsSheetState;
import com.nju.edu.erp.enums.sheetState.SaleReturnsSheetState;
import com.nju.edu.erp.model.vo.SaleReturns.SaleReturnsSheetVO;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.service.SaleReturnsService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SaleReturnsServiceImpl implements SaleReturnsService {

    //SaleReturnsSheetDao saleReturnsSheetDao;
    /**
     * 制定销售退货单
     *
     * @param saleReturnsSheetVO 销售退货单
     */
    @Override
    public void makeSaleReturnsSheet(UserVO userVO, SaleReturnsSheetVO saleReturnsSheetVO) {

    }

    /**
     * 根据状态获取销售退货单(state == null 则获取所有销售退货单)
     *
     * @param state 销售退货单状态
     * @return 销售退货单
     */
    @Override
    public List<SaleReturnsSheetVO> getSaleReturnsSheetByState(PurchaseReturnsSheetState state) {
        return null;
    }

    /**
     * 根据销售退货单id进行审批(state == "待二级审批"/"审批完成"/"审批失败")
     * 在controller层进行权限控制
     *
     * @param saleReturnsSheetId 进货退货单id
     * @param state              进货退货单修改后的状态
     */
    @Override
    public void approval(String saleReturnsSheetId, SaleReturnsSheetState state) {

    }
}
