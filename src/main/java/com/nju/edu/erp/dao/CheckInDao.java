package com.nju.edu.erp.dao;

import com.nju.edu.erp.model.po.CheckInPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface CheckInDao {

    int checkIn(CheckInPO InPO);

    List<CheckInPO> findAllByDate(Date date);

    Integer checkInCount(String name,String month);
}
