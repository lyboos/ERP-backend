package com.nju.edu.erp.model.po;

import com.nju.edu.erp.enums.sheetState.SaleReturnsSheetState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleReturnsSheetPO implements SheetPO {
    /**
     * 销售退货单单据编号（格式为：XSTHD-yyyyMMdd-xxxxx
     */
    private String id;

    /**
     * 关联的销售单id
     */
    private String SaleSheetId;

//    /**
//     * 客户/销售商id
//     */
//    private Integer supplier;
    /**
     * 业务员
     */
    private String salesman;
    /**
     * 操作员
     */
    private String operator;
    /**
     * 备注
     */
    private String remark;
    /**
     * 折让前退款商品的总额
     */
    private BigDecimal rawTotalAmount;
    /**
     * 折让
     */
    private BigDecimal discount;
    /**
     * 使用代金券总额
     */
    private BigDecimal voucherAmount;
    /**
     * 应退还总金额
     */
    private BigDecimal finalAmount;
    /**
     * 单据状态
     */
    private SaleReturnsSheetState state;
    /**
     * 创建时间
     */
    private Date createTime;
}
