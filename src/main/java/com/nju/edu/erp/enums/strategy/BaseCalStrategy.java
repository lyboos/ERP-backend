package com.nju.edu.erp.enums.strategy;

import com.nju.edu.erp.dao.CheckInDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BaseCalStrategy implements PaymentCalStrategyInterface {
    private static BaseCalStrategy b;

    @Autowired
    private CheckInDao checkInDao;

    /**
     * @param p
     * @return
     */
    @Override
    public BigDecimal calSalary(PaymentRelevantInfo p) {
        assert checkInDao != null;
        return p.getBaseSalary();
    }

    public static BaseCalStrategy getInstance() {
        if (b == null) {
            b = new BaseCalStrategy();
            return b;
        }
        return b;
    }
}
