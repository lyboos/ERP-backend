package com.nju.edu.erp.enums.strategy;

import com.nju.edu.erp.model.po.StaffInfoPO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRelevantInfo {
    private String name;
    private BigDecimal baseSalary; // from StaffInfo.baseSalary
    private BigDecimal commission;

    public PaymentRelevantInfo(StaffInfoPO staffInfoPO) {
        this.name = staffInfoPO.getName();
        this.baseSalary = staffInfoPO.getBaseSalary();
        this.commission = staffInfoPO.getCommission();
    }
}
