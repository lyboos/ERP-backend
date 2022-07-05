package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.CustomerDao;
import com.nju.edu.erp.enums.CustomerType;
import com.nju.edu.erp.model.po.CustomerPO;
import com.nju.edu.erp.model.vo.CustomerVO;
import com.nju.edu.erp.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDao customerDao;

    @Autowired
    public CustomerServiceImpl(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    /**
     * 根据id更新客户信息
     *
     * @param customerPO 客户信息
     */
    @Override
    public void updateCustomer(CustomerPO customerPO) {
        customerDao.updateOne(customerPO);
    }

    /**
     * 根据type查找对应类型的客户
     *
     * @param type 客户类型
     * @return 客户列表
     */
    @Override
    public List<CustomerPO> getCustomersByType(CustomerType type) {

        return customerDao.findAllByType(type);
    }

    /**
     * insert一个用户
     * @param vo
     * @return 正常情况返回影响行数，0代表失败，1代表成功
     */
    @Override
    public int insertCustomer(CustomerVO vo) {
        try{
            CustomerPO customerPO = new CustomerPO(vo.getId(),vo.getType(),vo.getLevel(),vo.getName(),vo.getPhone(),vo.getAddress(),vo.getZipcode(),vo.getEmail(),vo.getLineOfCredit(),vo.getReceivable(),vo.getPayable(),vo.getOperator());
            return customerDao.insertCustomer(customerPO);
        }catch (Exception e){
            return 0;
        }
    }

    /**
     * delete一个客户
     * @param vo
     * @return 影响的行数，若id无匹配返回0，因为不太会报错所以不使用try&catch
     */
    @Override
    public int deleteCustomer(CustomerVO vo) {
        CustomerPO customerPO = new CustomerPO(vo.getId(),vo.getType(),vo.getLevel(),vo.getName(),vo.getPhone(),vo.getAddress(),vo.getZipcode(),vo.getEmail(),vo.getLineOfCredit(),vo.getReceivable(),vo.getPayable(),vo.getOperator());

        return customerDao.deleteCustomer(customerPO);
    }


    @Override
    public CustomerPO findCustomerById(Integer supplier) {
        return customerDao.findOneById(supplier);
    }
}
