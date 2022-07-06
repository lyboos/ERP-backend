package com.nju.edu.erp.dao;

import com.nju.edu.erp.model.po.checkInPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface CheckInDao {

    int checkIn(checkInPO InPO);
}
