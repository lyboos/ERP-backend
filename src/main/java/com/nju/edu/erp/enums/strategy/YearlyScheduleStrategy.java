package com.nju.edu.erp.enums.strategy;

import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class YearlyScheduleStrategy implements PaymentScheduleStrategyInterface {
    static private YearlyScheduleStrategy sin;

    public static YearlyScheduleStrategy getInstance() {
        if (sin == null) {
            sin = new YearlyScheduleStrategy();
            return sin;
        }
        return sin;
    }

    /**
     * @return if today is payday
     */
    @Override
    public boolean isPayDay() {
        Date date = new Date();
        return date.getMonth() == Calendar.DECEMBER && date.getDate() == 31;
    }
}