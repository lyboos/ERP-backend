package com.nju.edu.erp.service;

import com.nju.edu.erp.enums.sheetState.PaymentState;
import com.nju.edu.erp.model.po.PaymentPO;
import com.nju.edu.erp.model.vo.Payment.PaymentVO;

public interface PaymentService extends SheetService<PaymentPO, PaymentVO, PaymentState> {
}
