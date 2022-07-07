package com.nju.edu.erp.enums.strategy;


import java.math.BigDecimal;

public interface PaymentCalStrategyInterface {
    BigDecimal calSalary(PaymentRelevantInfo p);
}
