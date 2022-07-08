package com.nju.edu.erp.service;

import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.enums.strategy.PaymentCalculatingStrategy;
import com.nju.edu.erp.enums.strategy.PaymentScheduleStrategy;
import com.nju.edu.erp.model.vo.StaffInfoVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
@SpringBootTest
class StaffServiceTest {
    @Autowired
    StaffService staffService;
    StaffInfoVO staffInfoVO = new StaffInfoVO(100,"hgd","男","2002-02-08","123456", Role.GM,new BigDecimal(1000000),new BigDecimal(0),5, PaymentCalculatingStrategy.YEARLY, PaymentScheduleStrategy.YEARLY,"123456");
    StaffInfoVO ly = new StaffInfoVO(101,"ly","男","2002-07-08","123456",Role.GM,new BigDecimal(30000),new BigDecimal(0),5,PaymentCalculatingStrategy.BASE,PaymentScheduleStrategy.MONTHLY,"ly123456");

    @Test
    @Transactional
    @Rollback
    void createNewStaff_1() {
        int res = staffService.createNewStaff(staffInfoVO);
        Assertions.assertEquals(1,res);
    }

    @Test
    @Transactional
    @Rollback
    void updateBaseSalary_1() {
        staffService.createNewStaff(ly);
        int res = staffService.updateBaseSalary(new BigDecimal(50000),101);
        Assertions.assertEquals(1,res);
    }

    @Test
    @Transactional
    @Rollback
    void updateLevel_1() {
        staffService.createNewStaff(ly);
        int res = staffService.updateLevel(4,101);
        Assertions.assertEquals(1,res);
    }

    @Test
    @Transactional
    @Rollback
    void updatePaymentCalStrategy_1() {
        staffService.createNewStaff(ly);
        int res = staffService.updatePaymentCalStrategy(PaymentCalculatingStrategy.COMMISSION,101);
        Assertions.assertEquals(1,res);
    }

    @Test
    @Transactional
    @Rollback
    void updatePaymentScheduleStrategy_1() {
        staffService.createNewStaff(ly);
        int res = staffService.updatePaymentScheduleStrategy(PaymentScheduleStrategy.MONTHLY,101);
        Assertions.assertEquals(1,res);
    }

    @Test
    @Transactional
    @Rollback
    void getStaffByName_1() {
        staffService.createNewStaff(ly);
        StaffInfoVO staffInfoVO1 = staffService.getStaffByName("ly");
        Assertions.assertEquals("ly",staffInfoVO1.getName());
    }
}