package com.nju.edu.erp.service;

import com.nju.edu.erp.model.vo.CheckInVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
class CheckInServiceTest {
    @Autowired
    CheckInService checkInService;
    //StaffInfoVO staffInfoVO = new StaffInfoVO(1,"zh","男","2002-03-21","1234567","xxx",new BigDecimal(1),new BigDecimal(2),3,new SalariedClassification(),new MonthlySchedule(),"hhh");
    CheckInVO checkInVO = new CheckInVO("ly","2020-03-28");

    @Test
    @Transactional
    @Rollback
    void checkIn_1() {//加成功了返回1
        int res = checkInService.checkIn(checkInVO);
        Assertions.assertEquals(1,res);
    }

    @Test
    @Transactional
    @Rollback
    void checkIn_2() {//重复打卡返回0
        checkInService.checkIn(checkInVO);
        int res = checkInService.checkIn(checkInVO);
        Assertions.assertEquals(0,res);
    }

    @Test
    @Transactional
    @Rollback
    void findAllByDate_1(){//有两个记录
        CheckInVO VO1 = new CheckInVO("hgd","2020-02-28");
        CheckInVO VO2 = new CheckInVO("lhr","2020-02-28");
        checkInService.checkIn(VO1);
        checkInService.checkIn(VO2);
        List<CheckInVO> res = checkInService.findAllByDate("2020-02-28");
        for (CheckInVO inVO:res){
            System.out.println(inVO.getName()+" "+inVO.getDate());
        }
    }

    @Test
    @Transactional
    @Rollback
    void findAllByDate_2(){//没有记录
        List<CheckInVO> res = checkInService.findAllByDate("2020-02-28");
        for (CheckInVO inVO:res){
            System.out.println(inVO.getName()+" "+inVO.getDate());
        }
    }
}