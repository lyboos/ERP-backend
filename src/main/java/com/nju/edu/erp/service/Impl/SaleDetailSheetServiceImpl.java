package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.model.vo.saleDetail.SaleDetailReplyVO;
import com.nju.edu.erp.model.vo.saleDetail.SaleDetailRequestVO;
import com.nju.edu.erp.model.vo.sale.SaleSheetVO;
import com.nju.edu.erp.model.vo.saleReturns.SaleReturnsSheetVO;
import com.nju.edu.erp.service.SaleDetailSheetService;
import com.nju.edu.erp.service.SaleReturnsService;
import com.nju.edu.erp.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaleDetailSheetServiceImpl implements SaleDetailSheetService {
    SaleService saleService;

    SaleReturnsService returnsService;

    @Autowired
    public SaleDetailSheetServiceImpl(SaleService saleService, SaleReturnsService returnsService) {
        this.saleService = saleService;
        this.returnsService = returnsService;
    }

    @Override
    public SaleDetailReplyVO request(SaleDetailRequestVO request) {
        DateComparator dateComparator = new DateComparator(request.getStartTime(), request.getEndTime());

        // null to get all the sheets
        List<SaleSheetVO> saleSheetVOList = saleService.getSaleSheetByState(null).stream()
                .filter(vo -> dateComparator.inBetween(vo.getCreateTime())).collect(Collectors.toList());
        List<SaleReturnsSheetVO> returnsSheetVOList = returnsService.getSaleReturnsSheetByState(null).stream()
                .filter(vo -> dateComparator.inBetween(vo.getCreateTime())).collect(Collectors.toList());

        SaleDetailReplyVO vo = new SaleDetailReplyVO();
        vo.setSaleSheetVOList(saleSheetVOList);
        vo.setSaleReturnsSheetVOList(returnsSheetVOList);

        return vo;
    }
}
