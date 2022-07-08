package com.nju.edu.erp.dao;

import com.nju.edu.erp.enums.strategy.PaymentCalculatingStrategy;
import com.nju.edu.erp.enums.strategy.PaymentScheduleStrategy;
import com.nju.edu.erp.model.po.StaffInfoPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
@Mapper
public interface StaffDao {
    /**
     * 员工入职
     * @param staffInfoPO 员工信息
     * @return 1成功，0失败
     */
    int createNewStaff(StaffInfoPO staffInfoPO);

    /**
     * 更新基本工资、岗位级别、薪资计算方式和发放方式的各种方法
     * @param baseSalary 等
     * @return 1代表成功，0代表失败
     */
    int updateBaseSalary(BigDecimal baseSalary,Integer id);
    int updateLevel(Integer level,Integer id);
    int updatePaymentCalStrategy(PaymentCalculatingStrategy pcs,Integer id);
    int updatePaymentScheduleStrategy(PaymentScheduleStrategy pss,Integer id);

    /**
     * 根据名字查找员工相关信息
     * @param name 员工姓名
     * @return 员工信息
     */
    StaffInfoPO getStaffByName(String name);
}
