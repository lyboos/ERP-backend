package com.nju.edu.erp.enums.strategy;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class YearlyStrategy implements PaymentCalStrategyInterface {
    static private YearlyStrategy sin;

    /**
     * @param p
     * @return
     */
    @Override
    public BigDecimal calSalary(PaymentRelevantInfo p) {
        //因为是年薪所以直接返回
        return p.getBaseSalary();
    }

    public static YearlyStrategy getInstance() {
        if (sin == null) {
            sin = new YearlyStrategy();
            return sin;
        }
        return sin;
    }
}
