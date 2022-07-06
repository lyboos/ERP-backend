package com.nju.edu.erp.service;

import com.nju.edu.erp.model.vo.StaffInfoVO;
import org.springframework.stereotype.Service;

@Service
public interface HumanResourcesService {
    /**
     * 员工入职
     */

    /**
     * 设定基本信息
     */

    /**
     * 员工打卡
     * @param staffInfoVO 员工个人信息
     * @param date 打卡日期，形式为“yyyy-MM-dd”
     * @return 0代表重复打卡，1代表打卡成功，只有当name与date均重复才算一次重复打卡
     */
    int checkIn(StaffInfoVO staffInfoVO, String date);
}
