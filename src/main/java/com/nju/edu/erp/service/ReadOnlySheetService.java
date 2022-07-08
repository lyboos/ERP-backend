package com.nju.edu.erp.service;

import com.nju.edu.erp.model.vo.ReadOnlyReplyVO;
import com.nju.edu.erp.model.vo.ReadOnlyRequestVO;

public interface ReadOnlySheetService<Q extends ReadOnlyRequestVO, A extends ReadOnlyReplyVO> {
    A request(Q request);
}
