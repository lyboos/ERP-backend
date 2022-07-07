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
     * @return
     */
    int updateBaseSalary(BigDecimal baseSalary);
    int updateLevel(Integer level);
    int updatePaymentCalStrategy(PaymentCalculatingStrategy pcs);
    int updatePaymentScheduleStrategy(PaymentScheduleStrategy pss);
}
