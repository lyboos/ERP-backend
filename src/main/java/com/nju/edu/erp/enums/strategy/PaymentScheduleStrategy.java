package com.nju.edu.erp.enums.strategy;

import com.nju.edu.erp.enums.BaseEnum;

public enum PaymentScheduleStrategy implements BaseEnum<PaymentScheduleStrategy, String> {
    MONTHLY("按月给付", MonthlyScheduleStrategy.getInstance()),
    YEARLY("按年给付", YearlyScheduleStrategy.getInstance())
    ;

    private final String value;

    private final PaymentScheduleStrategyInterface p;

    PaymentScheduleStrategy(String value, PaymentScheduleStrategyInterface p) {
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

    public boolean isPayday() {
        return p.isPayDay();
    }
}
