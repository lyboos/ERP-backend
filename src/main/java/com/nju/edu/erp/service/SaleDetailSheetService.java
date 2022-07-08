package com.nju.edu.erp.service;

import com.nju.edu.erp.model.vo.SaleDetailReplyVO;
import com.nju.edu.erp.model.vo.SaleDetailRequestVO;
import org.springframework.stereotype.Service;

@Service
public interface SaleDetailSheetService {
    SaleDetailReplyVO getSaleDetails(SaleDetailRequestVO request);
}
