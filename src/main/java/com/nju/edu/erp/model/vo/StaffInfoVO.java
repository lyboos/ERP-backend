package com.nju.edu.erp.model.vo;

import com.nju.edu.erp.model.PaymentStrategy.PaymentClassification;
import com.nju.edu.erp.model.PaymentStrategy.PaymentSchedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StaffInfoVO {// 员工信息VO
    /**
     * id
     */
    private int id;

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
    private Integer baseSalary;

    /**
     * 岗位工资
     */
    private Integer jobSalary;

    /**
     * 岗位级别
     */
    private Integer level;

    /**
     * 薪资计算方式
     */
    private PaymentClassification paymentClassification;

    /**
     * 薪资发放方式
     */
    private PaymentSchedule paymentSchedule;

    /**
     * 工资卡账户
     */
    private String salaryAccount;
}
