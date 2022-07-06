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
public class BankAccountPO {
    /**
     * 账户id
     */
    private String id;

    /**
     * 账户名
     */
    private String name;

    /**
     * 账户余额，不可直接修改！只能通过payment/receipt进行变动
     */
    private BigDecimal remaining_sum;
}
