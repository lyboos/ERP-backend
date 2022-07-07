package com.nju.edu.erp.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalarySheetPO implements SheetPO {
    /**
     * 工资单编号，格式为: GZD-yyyyMMdd-xxxxx
     */
    private String id;

    /**
     * staff id
     */
    private Integer staffId;

    /**
     * staff name
     */
    private String name;

    /**
     * bank account of the staff
     */
    private String salaryAccount;

    /**
     * 应发工资。已经计算过工资策略（提成等）、打卡缺席等工资影响因素，未计算税款等
     */
    private BigDecimal rawSalary;

    /**
     * 扣除税款，包括个人所得税、失业保险、住房公积金
     */
    private BigDecimal taxes;

    /**
     * 实发金额
     */
    private BigDecimal finalSalary;

    private Date createTime;
}
