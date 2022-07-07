package com.nju.edu.erp.model.vo;

import com.nju.edu.erp.enums.strategy.PaymentCalculatingStrategy;
import com.nju.edu.erp.enums.strategy.PaymentScheduleStrategy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StaffInfoVO {// 员工信息VO
    /**
     * id
     */
    private Integer id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String sex;

    /**
     * 出生日期
     */
    private String birthday;

    /**
     * 手机
     */
    private String phone;

    /**
     * 工作岗位
     */
    private String job;

    /**
     * 基本工资
     */
    private BigDecimal baseSalary;

    /**
     * 提成工资
     */
    private BigDecimal commission;

    /**
     * 岗位级别
     */
    private Integer level;

    /**
     * 薪资计算方式
     */
    private PaymentCalculatingStrategy paymentCalculatingStrategy;

    /**
     * 薪资发放方式
     */
    private PaymentScheduleStrategy paymentScheduleStrategy;

    /**
     * 工资卡账户
     */
    private String salaryAccount;
}
