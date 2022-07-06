package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.CheckInDao;
import com.nju.edu.erp.model.po.checkInPO;
import com.nju.edu.erp.model.vo.StaffInfoVO;
import com.nju.edu.erp.service.CheckInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class CheckInServiceImpl implements CheckInService {

    private final CheckInDao checkInDao;

    @Autowired
    public CheckInServiceImpl(CheckInDao checkInDao){
        this.checkInDao = checkInDao;
    }

    @Override
    public int checkIn(StaffInfoVO staffInfoVO, String date) {
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        try{
            Date _date =dateFormat.parse(date);
            checkInPO InPO = new checkInPO(staffInfoVO.getName(),_date);
            return checkInDao.checkIn(InPO);
        }catch (Exception e) {
            return 0;
        }

    }

}
