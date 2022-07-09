package com.nju.edu.erp.model.po;

import com.nju.edu.erp.enums.sheetState.RandPState;
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
public class PaymentPO implements SheetWithContentPO<PaymentContentPO> {
    /**
     * 付款单单据编号（格式为：FKD-yyyyMMdd-xxxxx）
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

    private RandPState state;

    private Date createTime;

    // 转账列表是一个content，外键是这个收款单
}
