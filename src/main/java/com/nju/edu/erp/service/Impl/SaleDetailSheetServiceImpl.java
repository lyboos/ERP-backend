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
        List<SaleSheetVO> saleSheetVOList = saleService.getSaleSheetByState(null); // null to get all the sheets
        List<SaleReturnsSheetVO> returnsSheetVOList = returnsService.getSaleReturnsSheetByState(null); // null to get all the sheets

        SaleDetailReplyVO vo = new SaleDetailReplyVO();
        vo.setSaleSheetVOList(saleSheetVOList);
        vo.setSaleReturnsSheetVOList(returnsSheetVOList);

        return vo;
    }
}
