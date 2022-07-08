package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.model.vo.SalarySheetVO;
import com.nju.edu.erp.model.vo.businessCondition.BusinessConditionReplyVO;
import com.nju.edu.erp.model.vo.businessCondition.BusinessConditionRequestVO;
import com.nju.edu.erp.model.vo.purchase.PurchaseSheetVO;
import com.nju.edu.erp.model.vo.purchaseReturns.PurchaseReturnsSheetVO;
import com.nju.edu.erp.model.vo.sale.SaleSheetVO;
import com.nju.edu.erp.model.vo.saleReturns.SaleReturnsSheetVO;
import com.nju.edu.erp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BusinessConditionServiceImpl implements BusinessConditionService {

    SaleService saleService;
    SaleReturnsService saleReturnsService;
    PurchaseService purchaseService;
    PurchaseReturnsService purchaseReturnsService;
    PaymentService paymentService;
    ReceiptService receiptService;
    SalarySheetService salarySheetService;
    CustomerService customerService;

    @Autowired
    public BusinessConditionServiceImpl(SaleService saleService, SaleReturnsService saleReturnsService, PurchaseService purchaseService, PurchaseReturnsService purchaseReturnsService, PaymentService paymentService, ReceiptService receiptService, SalarySheetService salarySheetService, CustomerService customerService) {
        this.saleService = saleService;
        this.saleReturnsService = saleReturnsService;
        this.purchaseService = purchaseService;
        this.purchaseReturnsService = purchaseReturnsService;
        this.paymentService = paymentService;
        this.receiptService = receiptService;
        this.salarySheetService = salarySheetService;
        this.customerService = customerService;
    }
    
    @Override
    public BusinessConditionReplyVO request(BusinessConditionRequestVO request) {
        DateComparator dateComparator = new DateComparator(request.getStartTime(), request.getEndTime());

        List<SaleSheetVO> saleSheetVOList = saleService.getSaleSheetByState(null).stream()
                .filter(vo -> dateComparator.inBetween(vo.getCreateTime())) // 时间
                .collect(Collectors.toList());
        List<SaleReturnsSheetVO> saleReturnsSheetVOList = saleReturnsService.getSaleReturnsSheetByState(null).stream()
                .filter(vo -> dateComparator.inBetween(vo.getCreateTime()))
                // 如果某return单对应的sale单不在saleSheetVOList里，则显然该return单与本次查询无关
                .collect(Collectors.toList());
        List<PurchaseSheetVO> purchaseSheetVOList = purchaseService.getPurchaseSheetByState(null).stream()
                .filter(vo -> dateComparator.inBetween(vo.getCreateTime())) // 时间
                .collect(Collectors.toList());
        List<PurchaseReturnsSheetVO> purchaseReturnsSheetVOList = purchaseReturnsService.getPurchaseReturnsSheetByState(null).stream()
                .filter(vo -> dateComparator.inBetween(vo.getCreateTime())) // 时间
                .collect(Collectors.toList());
        List<SalarySheetVO> salarySheetVOList = salarySheetService.getSheetByState(null).stream()
                .filter(vo -> dateComparator.inBetween(vo.getCreateTime())) // 时间
                .collect(Collectors.toList());

        BigDecimal income = BigDecimal.ZERO;
        BigDecimal discount = BigDecimal.ZERO;
        for (SaleSheetVO vo: saleSheetVOList) {
            income = income.add(vo.getFinalAmount()); // 纯收入
            discount = discount.add(vo.getRawTotalAmount().subtract(vo.getFinalAmount())); // 包括折让和代金券
        }
        for (SaleReturnsSheetVO vo: saleReturnsSheetVOList) {
            income = income.subtract(vo.getFinalAmount()); // 纯退款
            discount = discount.subtract(vo.getRawTotalAmount().subtract(vo.getFinalAmount())); // 包括折让和代金券
        }

        BigDecimal outcome = BigDecimal.ZERO;
        for (PurchaseSheetVO vo: purchaseSheetVOList) {
            outcome = outcome.add(vo.getTotalAmount());
        }
        for (PurchaseReturnsSheetVO vo: purchaseReturnsSheetVOList) {
            outcome = outcome.subtract(vo.getTotalAmount());
        }
        for (SalarySheetVO vo: salarySheetVOList) {
            outcome = outcome.add(vo.getFinalSalary());
        }

        BusinessConditionReplyVO reply = new BusinessConditionReplyVO();
        reply.setFinalIncome(income);
        reply.setDiscountSum(discount);
        reply.setTotalOutcome(outcome);
        reply.setProfit(income.subtract(outcome));

        return reply;
    }
}
