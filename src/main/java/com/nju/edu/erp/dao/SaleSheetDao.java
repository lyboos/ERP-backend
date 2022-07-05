package com.nju.edu.erp.dao;


import com.nju.edu.erp.enums.sheetState.SaleSheetState;
import com.nju.edu.erp.model.po.SaleSheetContentPO;
import com.nju.edu.erp.model.po.SaleSheetPO;
import com.nju.edu.erp.model.po.CustomerPurchaseAmountPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface SaleSheetDao extends SheetWithContentDao<SaleSheetPO, SaleSheetContentPO, SaleSheetState> {
    /**
     * 获取某个销售人员某段时间内消费总金额最大的客户(不考虑退货情况,销售单不需要审批通过,如果这样的客户有多个，仅保留一个)
     * @param salesman 销售人员的名字
     * @param beginTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    CustomerPurchaseAmountPO getMaxAmountCustomerOfSalesmanByTime(String salesman, Date beginTime,Date endTime);
}
