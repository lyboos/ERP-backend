package com.nju.edu.erp.service;

import com.nju.edu.erp.model.po.CustomerPO;
import com.nju.edu.erp.model.vo.CustomerVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
@SpringBootTest
class CustomerServiceTest {
    @Autowired
    CustomerService customerService;
    CustomerPO customerPO = new CustomerPO(3,"供应商",10,"hhhhh","12306","NJU","123456","114514@abc.com",new BigDecimal(0.00),new BigDecimal(0.00),new BigDecimal(60000.00),"uncln");

    @Test
    void insertCustomer() {//用来证明可用性
        int result = customerService.insertCustomer(customerPO);
        Assertions.assertEquals(1,result);
        customerService.deleteCustomer(customerPO);
    }

    @Test
    void deleteCustomer() {//用来证明可用性
        customerService.insertCustomer(customerPO);
        int result = customerService.deleteCustomer(customerPO);
        Assertions.assertEquals(1,result);
    }

    @Test
    void insertCustomer_Test1(){//证明如果添加id相同的客户会返回0
        int result = customerService.insertCustomer(customerPO);
        result = customerService.insertCustomer(customerPO);
        Assertions.assertEquals(0,result);
        customerService.deleteCustomer(customerPO);
    }

}