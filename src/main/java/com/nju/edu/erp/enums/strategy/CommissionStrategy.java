package com.nju.edu.erp.enums.strategy;

import com.nju.edu.erp.dao.CheckInDao;
import com.nju.edu.erp.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        Date date=new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        String month = df.format(date);

        int checkInSum = checkInDao.checkInCount(p.getName(),month);

        BigDecimal commission = p.getCommission().multiply(saleService.getMonthAmountBySalesman(p.getName()));

        return (p.getBaseSalary().add(commission)).multiply(new BigDecimal(checkInSum/30));
    }

    public static CommissionStrategy getInstance() {
        if (sin == null) {
            sin = new CommissionStrategy();
            return sin;
        }
        return sin;
    }
}
