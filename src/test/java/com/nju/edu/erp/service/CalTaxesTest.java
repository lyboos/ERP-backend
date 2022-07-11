package com.nju.edu.erp.service;

import com.nju.edu.erp.service.Impl.SalarySheetServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import java.math.BigDecimal;

@SpringBootTest
public class CalTaxesTest { // 由于涉及小数计算，有精度丢失问题，所以我们容许误差在1元以内，即大约0.1%
    @Test
    void test1() { // 0-36000
        BigDecimal a = SalarySheetServiceImpl.calTaxes(BigDecimal.valueOf(10000));
        System.out.println(a);
        BigDecimal diff = a.subtract(BigDecimal.valueOf(1168.5)).abs();
        System.out.println(diff);
        Assertions.assertTrue(diff.compareTo(BigDecimal.valueOf(0.1)) < 0);
    }

    @Test
    void test2() { // 36000-144000
        BigDecimal a = SalarySheetServiceImpl.calTaxes(BigDecimal.valueOf(37000));
        System.out.println(a);
        BigDecimal diff = a.subtract(BigDecimal.valueOf(4728.45)).abs();
        System.out.println(diff);
        Assertions.assertTrue(diff.compareTo(BigDecimal.valueOf(1)) < 0);
    }

    @Test
    void test3() { // 144000-300000
        BigDecimal a = SalarySheetServiceImpl.calTaxes(BigDecimal.valueOf(150000));
        System.out.println(a);
        BigDecimal diff = a.subtract(BigDecimal.valueOf(26155)).abs();
        System.out.println(diff);
        Assertions.assertTrue(diff.compareTo(BigDecimal.valueOf(0.1)) < 0);
    }

    @Test
    void test4() { // 300000-420000
        BigDecimal a = SalarySheetServiceImpl.calTaxes(BigDecimal.valueOf(310000));
        System.out.println(a);
        BigDecimal diff = a.subtract(BigDecimal.valueOf(70120)).abs();
        System.out.println(diff);
        Assertions.assertTrue(diff.compareTo(BigDecimal.valueOf(0.1)) < 0);
    }

    @Test
    void test5() { // 420000-660000
        BigDecimal a = SalarySheetServiceImpl.calTaxes(BigDecimal.valueOf(500000));
        System.out.println(a);
        BigDecimal diff = a.subtract(BigDecimal.valueOf(132330)).abs();
        System.out.println(diff);
        Assertions.assertTrue(diff.compareTo(BigDecimal.valueOf(0.1)) < 0);
    }

    @Test
    void test6() { // 660000-960000
        BigDecimal a = SalarySheetServiceImpl.calTaxes(BigDecimal.valueOf(710000));
        System.out.println(a);
        BigDecimal diff = a.subtract(BigDecimal.valueOf(210765)).abs();
        System.out.println(diff);
        Assertions.assertTrue(diff.compareTo(BigDecimal.valueOf(0.1)) < 0);
    }

    @Test
    void test7() {
        BigDecimal a = SalarySheetServiceImpl.calTaxes(BigDecimal.valueOf(1000000));
        System.out.println(a);
        BigDecimal diff = a.subtract(BigDecimal.valueOf(330580)).abs();
        System.out.println(diff);
        Assertions.assertTrue(diff.compareTo(BigDecimal.valueOf(0.1)) < 0);
    }
}
