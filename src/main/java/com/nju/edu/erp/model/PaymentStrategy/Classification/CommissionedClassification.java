package com.nju.edu.erp.model.PaymentStrategy.Classification;

import com.nju.edu.erp.model.PaymentStrategy.PaymentClassification;

public class CommissionedClassification implements PaymentClassification {
    @Override
    public double calculatePayment() {
        return 0;
    }
}
