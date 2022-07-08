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
        try{
            UserVO userVO = new UserVO(staffInfoVO.getName(),staffInfoVO.getRole(),"123456");
            StaffInfoPO staffInfoPO = new StaffInfoPO();
            BeanUtils.copyProperties(staffInfoVO,staffInfoPO);
            userService.register(userVO);
            return staffDao.createNewStaff(staffInfoPO);
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    public int updateBaseSalary(BigDecimal baseSalary) {
        try{
            return staffDao.updateBaseSalary(baseSalary);
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    public int updateLevel(Integer level) {
        try{
            return staffDao.updateLevel(level);
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    public int updatePaymentCalStrategy(PaymentCalculatingStrategy pcs) {
        try{
            return staffDao.updatePaymentCalStrategy(pcs);
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    public int updatePaymentScheduleStrategy(PaymentScheduleStrategy pss) {
        try{
            return staffDao.updatePaymentScheduleStrategy(pss);
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    public StaffInfoVO getStaffByName(String name) {
        StaffInfoVO staffInfoVO = new StaffInfoVO();
        StaffInfoPO staffInfoPO= staffDao.getStaffByName(name);
        BeanUtils.copyProperties(staffInfoVO,staffInfoPO);
        return staffInfoVO;
    }
}
