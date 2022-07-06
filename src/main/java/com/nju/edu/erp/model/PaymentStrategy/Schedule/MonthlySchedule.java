package com.nju.edu.erp.model.PaymentStrategy.Schedule;

import com.nju.edu.erp.model.PaymentStrategy.PaymentSchedule;

public class MonthlySchedule implements PaymentSchedule {
    @Override
    public boolean isPayDay() {
        return false;
    }
}
