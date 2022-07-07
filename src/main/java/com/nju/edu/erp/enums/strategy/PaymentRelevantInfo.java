package com.nju.edu.erp.enums.strategy;

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
    private BigDecimal baseSalary; // from StaffInfo.baseSalary
    private BigDecimal commission;
}
