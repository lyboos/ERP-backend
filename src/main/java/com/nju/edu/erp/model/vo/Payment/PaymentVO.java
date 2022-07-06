package com.nju.edu.erp.model.vo.Payment;

import com.nju.edu.erp.enums.sheetState.ReceiptState;
import com.nju.edu.erp.model.vo.SheetWithContentVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentVO implements SheetWithContentVO<PaymentContentVO> {
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
     * 总额汇总，新建单据时前端传null(在后端计算总金额
     */
    private BigDecimal TotalAmount;

    /**
     * 单据状态, 新建单据时前端传null
     */
    private ReceiptState state;

    List<PaymentContentVO> sheetContent;
}
