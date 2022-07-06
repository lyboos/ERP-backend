package com.nju.edu.erp.dao;

import com.nju.edu.erp.enums.sheetState.PaymentState;
import com.nju.edu.erp.model.po.PaymentContentPO;
import com.nju.edu.erp.model.po.PaymentPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface PaymentDao extends SheetWithContentDao<PaymentPO, PaymentContentPO, PaymentState> {
}
