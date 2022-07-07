package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.CheckInDao;
import com.nju.edu.erp.model.po.checkInPO;
import com.nju.edu.erp.model.vo.checkInVO;
import com.nju.edu.erp.service.CheckInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
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
    public int checkIn(checkInVO checkInVO) {
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        try{
            Date _date =dateFormat.parse(checkInVO.getDate());
            checkInPO InPO = new checkInPO(checkInVO.getName(),_date);
            return checkInDao.checkIn(InPO);
        }catch (Exception e) {
            return 0;
        }

    }

}
