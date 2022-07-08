package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.model.vo.Payment.PaymentVO;
import com.nju.edu.erp.model.vo.SalarySheetVO;
import com.nju.edu.erp.model.vo.businessHistory.BusinessHistoryReplyVO;
import com.nju.edu.erp.model.vo.businessHistory.BusinessHistoryRequestVO;
import com.nju.edu.erp.model.vo.purchase.PurchaseSheetVO;
import com.nju.edu.erp.model.vo.purchaseReturns.PurchaseReturnsSheetVO;
import com.nju.edu.erp.model.vo.receipt.ReceiptVO;
import com.nju.edu.erp.model.vo.sale.SaleSheetVO;
import com.nju.edu.erp.model.vo.saleReturns.SaleReturnsSheetVO;
import com.nju.edu.erp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessHistoryServiceImpl implements BusinessHistoryService {

    SaleService saleService;
    SaleReturnsService saleReturnsService;
    PurchaseService purchaseService;
    PurchaseReturnsService purchaseReturnsService;
    PaymentService paymentService;
    ReceiptService receiptService;
    SalarySheetService salarySheetService;

    @Autowired
    public BusinessHistoryServiceImpl(SaleService saleService, SaleReturnsService saleReturnsService, PurchaseService purchaseService, PurchaseReturnsService purchaseReturnsService, PaymentService paymentService, ReceiptService receiptService, SalarySheetService salarySheetService) {
        this.saleService = saleService;
        this.saleReturnsService = saleReturnsService;
        this.purchaseService = purchaseService;
        this.purchaseReturnsService = purchaseReturnsService;
        this.paymentService = paymentService;
        this.receiptService = receiptService;
        this.salarySheetService = salarySheetService;
    }

    @Override
    public BusinessHistoryReplyVO request(BusinessHistoryRequestVO request) {
        BusinessHistoryReplyVO reply = new BusinessHistoryReplyVO();
        switch (request.getSheetType()) { // sale: 1, purchase: 2, finance: 3, warehouse: 4
            case 1:
                List<SaleSheetVO> saleSheetVOList = saleService.getSaleSheetByState(null);
                List<SaleReturnsSheetVO> saleReturnsSheetVOList = saleReturnsService.getSaleReturnsSheetByState(null);
                reply.setSaleSheetVOList(saleSheetVOList);
                reply.setSaleReturnsSheetVOList(saleReturnsSheetVOList);
                break;
            case 2:
                List<PurchaseSheetVO> purchaseSheetVOList = purchaseService.getPurchaseSheetByState(null);
                List<PurchaseReturnsSheetVO> purchaseReturnsSheetVOList = purchaseReturnsService.getPurchaseReturnsSheetByState(null);
                reply.setPurchaseSheetVOList(purchaseSheetVOList);
                reply.setPurchaseReturnsSheetVOList(purchaseReturnsSheetVOList);
                break;
            case 3:
                List<PaymentVO> paymentVOList = paymentService.getSheetByState(null);
                List<ReceiptVO> receiptVOList = receiptService.getSheetByState(null);
                List<SalarySheetVO> salarySheetVOList = salarySheetService.getSheetByState(null);
                reply.setPaymentVOList(paymentVOList);
                reply.setReceiptVOList(receiptVOList);
                reply.setSalarySheetVOList(salarySheetVOList);
                break;
            case 4:
                throw new RuntimeException("库存相关单据尚未实现");
            default:
                throw new RuntimeException("不支持的单据类型。只支持sale: 1, purchase: 2, finance: 3, warehouse: 4");
        }
        return reply;
    }
}
