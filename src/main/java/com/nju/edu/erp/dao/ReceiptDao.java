package com.nju.edu.erp.dao;

import com.nju.edu.erp.enums.sheetState.ReceiptState;
import com.nju.edu.erp.model.po.ReceiptContentPO;
import com.nju.edu.erp.model.po.ReceiptPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ReceiptDao extends SheetWithContentDao<ReceiptPO, ReceiptContentPO, ReceiptState> {
}
