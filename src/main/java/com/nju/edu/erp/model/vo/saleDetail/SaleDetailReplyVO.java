package com.nju.edu.erp.model.vo.saleDetail;

import com.nju.edu.erp.model.vo.ReadOnlyReplyVO;
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
public class SaleDetailReplyVO implements ReadOnlyReplyVO {
    List<SaleSheetVO> saleSheetVOList;

    List<SaleReturnsSheetVO> saleReturnsSheetVOList;
}
