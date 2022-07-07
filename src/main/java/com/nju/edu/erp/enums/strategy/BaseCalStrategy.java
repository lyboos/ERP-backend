package com.nju.edu.erp.enums.strategy;

import com.nju.edu.erp.dao.CheckInDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        Date date=new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        String month = df.format(date);
        return p.getBaseSalary().multiply(new BigDecimal(checkInDao.checkInCount(p.getName(),month)/30));
    }

    public static BaseCalStrategy getInstance() {
        if (b == null) {
            b = new BaseCalStrategy();
            return b;
        }
        return b;
    }
}
