package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.HumanResourcesDao;
import com.nju.edu.erp.model.po.checkInPO;
import com.nju.edu.erp.model.vo.StaffInfoVO;
import com.nju.edu.erp.service.HumanResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class HumanResourcesServiceImpl implements HumanResourcesService {

    private final HumanResourcesDao humanResourcesDao;

    @Autowired
    public HumanResourcesServiceImpl(HumanResourcesDao humanResourcesDao){
        this.humanResourcesDao = humanResourcesDao;
    }

    @Override
    public int checkIn(StaffInfoVO staffInfoVO, String date) {
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        try{
            Date _date =dateFormat.parse(date);
            checkInPO InPO = new checkInPO(staffInfoVO.getName(),_date);
            return humanResourcesDao.checkIn(InPO);
        }catch (ParseException e) {
            return 0;
        }
    }

//    @Override
//    public int checkIn(String name, String date) {
//        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
//        try{
//            Date _date =dateFormat.parse(date);
//            //checkInPO InPO = new checkInPO(staffInfoVO.getName(),_date);
//            checkInPO InPO = new checkInPO(name,_date);
//            return humanResourcesDao.checkIn(InPO);
//        }catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }
}
