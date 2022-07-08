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

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BusinessHistoryServiceImpl implements BusinessHistoryService {

    SaleService saleService;
    SaleReturnsService saleReturnsService;
    PurchaseService purchaseService;
    PurchaseReturnsService purchaseReturnsService;
    PaymentService paymentService;
    ReceiptService receiptService;
    SalarySheetService salarySheetService;

    CustomerService customerService;

    @Autowired
    public BusinessHistoryServiceImpl(SaleService saleService, SaleReturnsService saleReturnsService, PurchaseService purchaseService, PurchaseReturnsService purchaseReturnsService, PaymentService paymentService, ReceiptService receiptService, SalarySheetService salarySheetService, CustomerService customerService) {
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
    public BusinessHistoryReplyVO request(BusinessHistoryRequestVO request) {
        BusinessHistoryReplyVO reply = new BusinessHistoryReplyVO();
        DateComparator dateComparator = new DateComparator(request.getStartTime(), request.getEndTime());
        switch (request.getSheetType()) { // sale: 1, purchase: 2, finance: 3, warehouse: 4
            case 1:
                List<SaleSheetVO> saleSheetVOList = saleService.getSaleSheetByState(null).stream()
                        .filter(vo -> dateComparator.inBetween(vo.getCreateTime())) // 时间
                        .filter(vo -> customerService.findCustomerById(vo.getSupplier()).getName().equals(request.getCustomerName())) // 客户名
                        .filter(vo -> vo.getOperator().equals(request.getOperatorName())) // 操作员名
                        .collect(Collectors.toList());
                List<SaleReturnsSheetVO> saleReturnsSheetVOList = saleReturnsService.getSaleReturnsSheetByState(null).stream()
                        .filter(vo -> dateComparator.inBetween(vo.getCreateTime()))
                        // 如果某return单对应的sale单不在saleSheetVOList里，则显然该return单与本次查询无关；若在，则客户名与条件相等
                        .filter(vo -> saleSheetVOList.stream().anyMatch(saleSheetVO -> saleSheetVO.getId().equals(vo.getSaleSheetId())))
                        .filter(vo -> vo.getOperator().equals(request.getOperatorName())) // 操作员名
                        .collect(Collectors.toList());

                reply.setSaleSheetVOList(saleSheetVOList);
                reply.setSaleReturnsSheetVOList(saleReturnsSheetVOList);
                break;
            case 2:
                List<PurchaseSheetVO> purchaseSheetVOList = purchaseService.getPurchaseSheetByState(null).stream()
                        .filter(vo -> dateComparator.inBetween(vo.getCreateTime()))
                        .filter(vo -> customerService.findCustomerById(vo.getSupplier()).getName().equals(request.getCustomerName())) // 客户/供应商名
                        .filter(vo -> vo.getOperator().equals(request.getOperatorName())) // 操作员名
                        .collect(Collectors.toList());
                List<PurchaseReturnsSheetVO> purchaseReturnsSheetVOList = purchaseReturnsService.getPurchaseReturnsSheetByState(null).stream()
                        .filter(vo -> dateComparator.inBetween(vo.getCreateTime()))
                        .filter(vo -> purchaseSheetVOList.stream().anyMatch(saleSheetVO -> saleSheetVO.getId().equals(vo.getPurchaseSheetId())))
                        .filter(vo -> vo.getOperator().equals(request.getOperatorName())) // 操作员名
                        .collect(Collectors.toList());
                reply.setPurchaseSheetVOList(purchaseSheetVOList);
                reply.setPurchaseReturnsSheetVOList(purchaseReturnsSheetVOList);
                break;
            case 3:
                List<PaymentVO> paymentVOList = paymentService.getSheetByState(null).stream()
                        .filter(vo -> dateComparator.inBetween(vo.getCreateTime()))
                        .filter(vo -> customerService.findCustomerById(vo.getSupplier()).getName().equals(request.getCustomerName())) // 客户/供应商名
                        .filter(vo -> vo.getOperator().equals(request.getOperatorName())) // 操作员名
                        .collect(Collectors.toList());
                List<ReceiptVO> receiptVOList = receiptService.getSheetByState(null).stream()
                        .filter(vo -> dateComparator.inBetween(vo.getCreateTime()))
                        .filter(vo -> customerService.findCustomerById(vo.getSupplier()).getName().equals(request.getCustomerName())) // 客户/供应商名
                        .filter(vo -> vo.getOperator().equals(request.getOperatorName())) // 操作员名
                        .collect(Collectors.toList());
                List<SalarySheetVO> salarySheetVOList = salarySheetService.getSheetByState(null).stream()
                        .filter(vo -> dateComparator.inBetween(vo.getCreateTime())).collect(Collectors.toList());
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

class DateComparator {
    private final Date startTime;
    private final Date endTime;
    public DateComparator(Date startTime, Date endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public boolean inBetween(Date d) {
        boolean a = d.equals(startTime) || d.equals(endTime);
        boolean b = startTime.before(d) && d.before(endTime);
        return a || b;
    }
}