package com.nju.edu.erp.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReceiptContentPO implements SheetContentPO {
    // 自增id
    private Integer id;

    // 从属的收款单id
    private String sheetId;

    private String bankAccountId;

    private BigDecimal amount;

    private String remark;
}
