package com.nju.edu.erp.model.po;

import com.nju.edu.erp.enums.sheetState.ReceiptState;
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
public class ReceiptPO implements SheetWithContentPO<ReceiptContentPO> {
    /**
     * 收款单单据编号（格式为：SKD-yyyyMMdd-xxxxx）
     */
    private String id;
    /**
     * 客户/销售商id
     */
    private Integer supplier;
    /**
     * 操作员（当前登陆用户）
     */
    private String operator;
    /**
     * 总额汇总
     */
    private BigDecimal TotalAmount;

    private ReceiptState state;

    private Date createTime;

    // 转账列表是一个content，外键是这个收款单
}
