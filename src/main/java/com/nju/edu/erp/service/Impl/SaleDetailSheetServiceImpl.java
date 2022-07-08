package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.model.vo.saleDetail.SaleDetailReplyVO;
import com.nju.edu.erp.model.vo.saleDetail.SaleDetailRequestVO;
import com.nju.edu.erp.model.vo.sale.SaleSheetVO;
import com.nju.edu.erp.model.vo.saleReturns.SaleReturnsSheetVO;
import com.nju.edu.erp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaleDetailSheetServiceImpl implements SaleDetailSheetService {
    SaleService saleService;

    SaleReturnsService returnsService;

    CustomerService customerService;

    ProductService productService;

    @Autowired
    public SaleDetailSheetServiceImpl(SaleService saleService, SaleReturnsService returnsService, CustomerService customerService, ProductService productService) {
        this.saleService = saleService;
        this.returnsService = returnsService;
        this.customerService = customerService;
        this.productService = productService;
    }

    @Override
    public SaleDetailReplyVO request(SaleDetailRequestVO request) {
        DateComparator dateComparator = new DateComparator(request.getStartTime(), request.getEndTime());

        // Code03
        // null to get all the sheets
        // do the filtering process
        List<SaleSheetVO> saleSheetVOList = saleService.getSaleSheetByState(null).stream()
                .filter(vo -> dateComparator.inBetween(vo.getCreateTime())) // 时间
                .filter(vo -> vo.getSaleSheetContent().stream().anyMatch( // 商品名
                        content -> productService.getOneProductByPid(content.getPid()).getName().equals(request.getProductName()))
                )
                .filter(vo -> customerService.findCustomerById(vo.getSupplier()).getName().equals(request.getCustomerName())) // 客户名
                .filter(vo -> vo.getOperator().equals(request.getOperatorName())) // 操作员名
                .collect(Collectors.toList());

        // 洗掉无关content
        saleSheetVOList.forEach(
                saleSheetVO -> saleSheetVO.getSaleSheetContent().removeIf(
                        content -> !productService.getOneProductByPid(content.getPid()).getName().equals(request.getProductName())
                )
        );

        List<SaleReturnsSheetVO> returnsSheetVOList = returnsService.getSaleReturnsSheetByState(null).stream()
                .filter(vo -> dateComparator.inBetween(vo.getCreateTime()))
                .filter(vo -> vo.getSaleReturnsSheetContent().stream().anyMatch(
                        content -> productService.getOneProductByPid(content.getPid()).getName().equals(request.getProductName()))
                )
                // 如果某return单对应的sale单不在saleSheetVOList里，则显然该return单与本次查询无关；若在，则客户名与条件相等
                .filter(vo -> saleSheetVOList.stream().anyMatch(saleSheetVO -> saleSheetVO.getId().equals(vo.getSaleSheetId())))
                .filter(vo -> vo.getOperator().equals(request.getOperatorName()))
                .collect(Collectors.toList());

        // 洗掉无关content
        returnsSheetVOList.forEach(
                returnsSheetVO -> returnsSheetVO.getSaleReturnsSheetContent().removeIf(
                        content -> !productService.getOneProductByPid(content.getPid()).getName().equals(request.getProductName())
                )
        );

        SaleDetailReplyVO vo = new SaleDetailReplyVO();
        vo.setSaleSheetVOList(saleSheetVOList);
        vo.setSaleReturnsSheetVOList(returnsSheetVOList);

        return vo;
    }
}
