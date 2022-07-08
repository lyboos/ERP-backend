package com.nju.edu.erp.model.vo.businessHistory;

import com.nju.edu.erp.model.vo.Payment.PaymentVO;
import com.nju.edu.erp.model.vo.ReadOnlyReplyVO;
import com.nju.edu.erp.model.vo.SalarySheetVO;
import com.nju.edu.erp.model.vo.purchase.PurchaseSheetVO;
import com.nju.edu.erp.model.vo.purchaseReturns.PurchaseReturnsSheetVO;
import com.nju.edu.erp.model.vo.receipt.ReceiptVO;
import com.nju.edu.erp.model.vo.sale.SaleSheetVO;
import com.nju.edu.erp.model.vo.saleReturns.SaleReturnsSheetVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusinessHistoryReplyVO implements ReadOnlyReplyVO {

    private List<SaleSheetVO> saleSheetVOList;
    private List<SaleReturnsSheetVO> saleReturnsSheetVOList;

    private List<PurchaseSheetVO> purchaseSheetVOList;
    private List<PurchaseReturnsSheetVO> purchaseReturnsSheetVOList;

    private List<ReceiptVO> receiptVOList;
    private List<PaymentVO> paymentVOList;
    private List<SalarySheetVO> salarySheetVOList;
}
