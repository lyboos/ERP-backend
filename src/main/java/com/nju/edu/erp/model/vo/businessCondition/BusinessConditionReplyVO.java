package com.nju.edu.erp.model.vo.businessCondition;

import com.nju.edu.erp.model.vo.ReadOnlyReplyVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusinessConditionReplyVO implements ReadOnlyReplyVO {
    private BigDecimal finalIncome;
    private BigDecimal discountSum;
    private BigDecimal totalOutcome;
    private BigDecimal profit; // finalIncome - totalOutcome
}
