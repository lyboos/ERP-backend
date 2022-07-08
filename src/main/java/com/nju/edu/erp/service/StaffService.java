package com.nju.edu.erp.service;

import com.nju.edu.erp.enums.strategy.PaymentCalculatingStrategy;
import com.nju.edu.erp.enums.strategy.PaymentScheduleStrategy;
import com.nju.edu.erp.model.vo.StaffInfoVO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface StaffService {
    /**
     * 员工入职
     * @param staffInfoVO 员工信息
     * @return 1成功，0失败
     */
    int createNewStaff(StaffInfoVO staffInfoVO);

    /**
     * 更新基本工资、岗位级别、薪资计算方式和发放方式的各种方法
     * @param baseSalary
     * @return
     */
    int updateBaseSalary(BigDecimal baseSalary);
    int updateLevel(Integer level);
    int updatePaymentCalStrategy(PaymentCalculatingStrategy pcs);
    int updatePaymentScheduleStrategy(PaymentScheduleStrategy pss);

    StaffInfoVO getStaffByName(String name);
}
