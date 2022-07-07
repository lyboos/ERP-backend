package com.nju.edu.erp.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckInVO {
    /**
     * 姓名
     */
    private String name;

    /**
     * 签到日期
     */
    private String date;
}
