package com.nju.edu.erp.model.vo.receipt;

import com.nju.edu.erp.model.vo.SheetContentVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReceiptContentVO implements SheetContentVO {
    /**
     * 自增id, 新建单据时前端传null
     */
    private Integer id;
    /**
     * 单id, 新建单据时前端传null
     */
    private String sheetId;

    private String bankAccountId;

    private BigDecimal amount;

    private String remark;
}
