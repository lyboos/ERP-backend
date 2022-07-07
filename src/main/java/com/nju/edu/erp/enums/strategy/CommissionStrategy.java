package com.nju.edu.erp.enums.strategy;

import com.nju.edu.erp.dao.CheckInDao;
import com.nju.edu.erp.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CommissionStrategy implements PaymentCalStrategyInterface {
    static private CommissionStrategy sin;

    @Autowired
    private CheckInDao checkInDao;

    @Autowired
    private SaleService saleService;

    /**
     * @param p
     * @return
     */
    @Override
    public BigDecimal calSalary(PaymentRelevantInfo p) {
        return null;
    }

    public static CommissionStrategy getInstance() {
        if (sin == null) {
            sin = new CommissionStrategy();
            return sin;
        }
        return sin;
    }
}
