package com.nju.edu.erp.service;

import com.nju.edu.erp.enums.sheetState.RandPState;
import com.nju.edu.erp.model.vo.Payment.PaymentVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
class PaymentServiceTest {
    @Autowired
    PaymentService paymentService;

    @Test
    @Transactional
    @Rollback
    void getSheetByState_1() {//这里需要配合数据库使用
        List<PaymentVO> res = paymentService.getSheetByState(RandPState.SUCCESS);
        for (PaymentVO paymentVO:res){
            System.out.println(paymentVO.getSupplier());
            System.out.println(paymentVO.getState());
        }
    }

    @Test
    @Transactional
    @Rollback
    void getSheetById_1() {
        PaymentVO paymentVO = paymentService.getSheetById("FKD-000001-002");
        Assertions.assertTrue(paymentVO == null);
    }

    @Test
    @Transactional
    @Rollback
    void getSheetById_2() {
        PaymentVO paymentVO = paymentService.getSheetById("FKD-000001-001");
        System.out.println(paymentVO.getSupplier());
        System.out.println(paymentVO.getState());
    }
}