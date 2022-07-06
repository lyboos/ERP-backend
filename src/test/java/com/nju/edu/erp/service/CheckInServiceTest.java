package com.nju.edu.erp.service;

import com.nju.edu.erp.model.PaymentStrategy.Classification.SalariedClassification;
import com.nju.edu.erp.model.PaymentStrategy.Schedule.MonthlySchedule;
import com.nju.edu.erp.model.vo.StaffInfoVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@SpringBootTest
class CheckInServiceTest {
    @Autowired
    CheckInService checkInService;
    StaffInfoVO staffInfoVO = new StaffInfoVO(1,"zh","男","2002-03-21","1234567","xxx",new BigDecimal(1),new BigDecimal(2),3,new SalariedClassification(),new MonthlySchedule(),"hhh");

    @Test
    @Transactional
    @Rollback
    void checkIn_1() {//加成功了返回1
        int res = checkInService.checkIn(staffInfoVO,"2020-02-02");
        Assertions.assertEquals(1,res);
    }

    @Test
    @Transactional
    @Rollback
    void checkIn_2() {//重复打卡返回0
        checkInService.checkIn(staffInfoVO,"2020-02-01");
        int res = checkInService.checkIn(staffInfoVO,"2020-02-01");
        Assertions.assertEquals(0,res);
    }
}