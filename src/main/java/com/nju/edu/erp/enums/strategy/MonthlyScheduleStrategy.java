package com.nju.edu.erp.enums.strategy;

import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class MonthlyScheduleStrategy implements PaymentScheduleStrategyInterface {
    static private MonthlyScheduleStrategy sin;

    public static MonthlyScheduleStrategy getInstance() {
        if (sin == null) {
            sin = new MonthlyScheduleStrategy();
            return sin;
        }
        return sin;
    }

    @Override
    public boolean isPayDay() {
        Date date = new Date();
        if (date.getMonth() == Calendar.FEBRUARY) {
            if (isLeapYear(date.getYear() + 1900)) {
                return date.getDate() == 29;
            } else {
                return date.getDate() == 28;
            }
        } else {
            Set<Integer> smallMonth = new HashSet<>();
            smallMonth.add(Calendar.APRIL);
            smallMonth.add(Calendar.JUNE);
            smallMonth.add(Calendar.SEPTEMBER);
            smallMonth.add(Calendar.NOVEMBER);
            if (smallMonth.contains(date.getMonth())) {
                return date.getDate() == 30;
            } else {
                return date.getTime() == 31;
            }
        }
    }

    private static boolean isLeapYear(int year) {
        if (year % 4 != 0) return false;
        else if (year % 100 == 0 && year % 400 != 0) return false;
        else return true;
    }
}
