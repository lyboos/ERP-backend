package com.nju.edu.erp.model.vo.businessCondition;

import com.nju.edu.erp.model.vo.ReadOnlyReplyVO;
import com.nju.edu.erp.model.vo.ReadOnlyRequestVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusinessConditionRequestVO implements ReadOnlyRequestVO {
    private Date startTime;
    private Date endTime;
}
