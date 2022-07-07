package com.nju.edu.erp.service;

import com.nju.edu.erp.model.vo.CheckInVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CheckInService {

    /**
     * 员工打卡
     * @param checkInVO 打卡VO包括了String类型的name和date
     * @return 0代表重复打卡，1代表打卡成功，只有当name与date均重复才算一次重复打卡
     */
    int checkIn(CheckInVO checkInVO);

    /**
     * 根据输入的日期来查看当日目前打卡人员名单
     * @param date 日期
     * @return 打卡人员名单
     */
    List<CheckInVO> findAllByDate(String date);
}
