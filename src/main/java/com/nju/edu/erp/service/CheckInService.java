package com.nju.edu.erp.service;

import com.nju.edu.erp.model.vo.checkInVO;
import org.springframework.stereotype.Service;

@Service
public interface CheckInService {

    /**
     * 员工打卡
     * @param checkInVO 打卡VO包括了String类型的name和date
     * @return 0代表重复打卡，1代表打卡成功，只有当name与date均重复才算一次重复打卡
     */
    int checkIn(checkInVO checkInVO);
}
