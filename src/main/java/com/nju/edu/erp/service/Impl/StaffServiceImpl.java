package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.StaffDao;
import com.nju.edu.erp.enums.strategy.PaymentCalculatingStrategy;
import com.nju.edu.erp.enums.strategy.PaymentScheduleStrategy;
import com.nju.edu.erp.model.po.StaffInfoPO;
import com.nju.edu.erp.model.vo.StaffInfoVO;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.service.StaffService;
import com.nju.edu.erp.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class StaffServiceImpl implements StaffService {

    private final StaffDao staffDao;
    private final UserService userService;

    @Autowired
    public StaffServiceImpl(StaffDao staffDao,UserService userService){
        this.staffDao = staffDao;
        this.userService = userService;
    }

    @Override
    public int createNewStaff(StaffInfoVO staffInfoVO) {

            UserVO userVO = new UserVO(staffInfoVO.getName(),staffInfoVO.getRole(),"123456");
            StaffInfoPO staffInfoPO = new StaffInfoPO();
            BeanUtils.copyProperties(staffInfoVO,staffInfoPO);
            userService.register(userVO);
            return staffDao.createNewStaff(staffInfoPO);

    }

    @Override
    public int updateBaseSalary(BigDecimal baseSalary,Integer id) {
        try{
            return staffDao.updateBaseSalary(baseSalary,id);
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    public int updateLevel(Integer level,Integer id) {
        try{
            return staffDao.updateLevel(level,id);
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    public int updatePaymentCalStrategy(PaymentCalculatingStrategy pcs,Integer id) {
        try{
            return staffDao.updatePaymentCalStrategy(pcs,id);
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    public int updatePaymentScheduleStrategy(PaymentScheduleStrategy pss,Integer id) {
        try{
            return staffDao.updatePaymentScheduleStrategy(pss,id);
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    public StaffInfoVO getStaffByName(String name) {
        StaffInfoVO staffInfoVO = new StaffInfoVO();
        StaffInfoPO staffInfoPO= staffDao.getStaffByName(name);
        BeanUtils.copyProperties(staffInfoPO,staffInfoVO);//前面一个是转换前，后一个是转换后
        return staffInfoVO;
    }
}
