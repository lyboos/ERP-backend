package com.nju.edu.erp.enums.strategy;

import com.nju.edu.erp.enums.BaseEnum;

import java.math.BigDecimal;

public enum PaymentCalculatingStrategy implements BaseEnum<PaymentCalculatingStrategy, String> {
    BASE("月薪", BaseCalStrategy.getInstance()),
    COMMISSION("基本工资加提成", CommissionStrategy.getInstance()),
    YEARLY("年薪", YearlyStrategy.getInstance())
    ;

    private final String value;
    private final PaymentCalStrategyInterface p;

    PaymentCalculatingStrategy(String value, PaymentCalStrategyInterface p) {
        this.value = value;
        this.p = p;
    }

    /**
     * 获取枚举的值
     *
     * @return 枚举的值
     */
    @Override
    public String getValue() {
        return value;
    }

    public BigDecimal getAmount(PaymentRelevantInfo info) {
        return p.calSalary(info);
    }
}
