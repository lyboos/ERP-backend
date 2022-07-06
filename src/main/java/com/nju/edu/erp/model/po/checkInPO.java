package com.nju.edu.erp.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class checkInPO {
    /**
     * 姓名
     */
    private String name;

    /**
     * 签到日期
     */
    private Date date;
}
