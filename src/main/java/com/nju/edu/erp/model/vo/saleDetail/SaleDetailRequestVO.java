package com.nju.edu.erp.model.vo.saleDetail;

import com.nju.edu.erp.model.vo.ReadOnlyRequestVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleDetailRequestVO implements ReadOnlyRequestVO {
    private Date startTime;

    private Date endTime;

    private String productName;

    private String customerName;

    private String operatorName;
}
