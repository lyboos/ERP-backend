package com.nju.edu.erp.model.vo.saleReturns;

import com.nju.edu.erp.enums.sheetState.SaleSheetState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleReturnsSheetVO {
    /**
     * 销售退货单单据编号（格式为：XSTHD-yyyyMMdd-xxxxx), 新建单据时前端传null
     */
    private String id;
//    /**
//     * 供应商id
//     */
//    private Integer supplier;

    private String saleSheetId;
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
     * 折让前总额
     */
    private BigDecimal rawTotalAmount;
    /**
     * 单据状态, 新建单据时前端传null
     */
    private SaleSheetState state;
    /**
     * 应退还总金额, 新建单据时前端传null
     */
    private BigDecimal finalAmount;
    /**
     * 折扣
     */
    private BigDecimal discount;
    /**
     * 使用代金券总额
     */
    private BigDecimal voucherAmount;
    /**
     * 进货单具体内容
     */
    List<SaleReturnsSheetContentVO> saleReturnsSheetContent;

    private Date createTime;
}
