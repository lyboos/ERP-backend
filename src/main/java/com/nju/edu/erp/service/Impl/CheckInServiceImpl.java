package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.CheckInDao;
import com.nju.edu.erp.model.po.CheckInPO;
import com.nju.edu.erp.model.vo.CheckInVO;
import com.nju.edu.erp.service.CheckInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CheckInServiceImpl implements CheckInService {

    private final CheckInDao checkInDao;

    @Autowired
    public CheckInServiceImpl(CheckInDao checkInDao){
        this.checkInDao = checkInDao;
    }

    @Override
    public int checkIn(String name) {
        try{
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = formatter.format(new Date());
            String tmp = dateString;
            Date date = formatter.parse(tmp);
            CheckInPO checkInPO = new CheckInPO(name,date);
            return checkInDao.checkIn(checkInPO);
        }catch (Exception e) {
            return 0;
        }
    }

    @Override
    public List<CheckInVO> findAllByDate(String date) {
        List<CheckInVO> res = new ArrayList<>();
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        try{
            Date _date =dateFormat.parse(date);
            List<CheckInPO> all = checkInDao.findAllByDate(_date);
            for (CheckInPO checkInPO:all){
                CheckInVO checkInVO = new CheckInVO();
                checkInVO.setName(checkInPO.getName());
                checkInVO.setDate(checkInPO.getDate().toString());
                res.add(checkInVO);
            }
        }catch (Exception e) {
            return null;
        }
        return res;
    }

    @Override
    public int checkInCount(String name) {
        Date date=new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        String month = df.format(date);
        return checkInDao.checkInCount(name,month);
    }

}
