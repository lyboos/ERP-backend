package com.nju.edu.erp.model.vo.businessHistory;

import com.nju.edu.erp.model.vo.ReadOnlyRequestVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.Inet4Address;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusinessHistoryRequestVO implements ReadOnlyRequestVO {
    private Date startTime;

    private Date endTime;

    private Integer sheetType; // sale: 1, purchase: 2, finance: 3, warehouse: 4.

    private String customerName;

    private String operatorName;
}
