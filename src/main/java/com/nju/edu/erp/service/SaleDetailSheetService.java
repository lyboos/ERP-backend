package com.nju.edu.erp.service;

import com.nju.edu.erp.model.vo.saleDetail.SaleDetailReplyVO;
import com.nju.edu.erp.model.vo.saleDetail.SaleDetailRequestVO;
import org.springframework.stereotype.Service;

@Service
public interface SaleDetailSheetService extends ReadOnlySheetService<SaleDetailRequestVO, SaleDetailReplyVO> {
    SaleDetailReplyVO request(SaleDetailRequestVO request);
}
