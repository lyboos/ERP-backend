package com.nju.edu.erp.service.Impl.promotionStrategy;

import com.nju.edu.erp.model.po.SaleSheetContentPO;
import com.nju.edu.erp.model.po.SaleSheetPO;
import com.nju.edu.erp.model.vo.sale.SaleSheetVO;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.List;

public class AdditionalDiscountStrategy implements PromotionStrategy {

    private final BigDecimal additionalDiscount;
    private final BigDecimal trigger; // 销售单rawTotalAmount >= trigger的时候触发额外折扣

    public AdditionalDiscountStrategy(BigDecimal additionalDiscount, BigDecimal trigger) {
        this.additionalDiscount = additionalDiscount;
        this.trigger = trigger;
    }

    /**
     * 在生成PO内容前对前端传入的VO进行预处理，不修改原VO内容，
     * 而是生成一个新的修改过的VO。比如一定比例会提高代金券、折让率、满减等
     *
     * @param resVO
     * @param calVO
     * @param calPO
     */
    @Override
    public void preProcessVO(SaleSheetVO resVO, SaleSheetVO calVO, SaleSheetPO calPO, List<SaleSheetContentPO> calPOList) {
        if (calPO.getRawTotalAmount().compareTo(trigger) >= 0) {
            resVO.setDiscount(resVO.getDiscount().add(additionalDiscount));
        }
    }
}
