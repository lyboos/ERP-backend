package com.nju.edu.erp.service.Impl.promotionStrategy;

import com.nju.edu.erp.model.po.SaleSheetContentPO;
import com.nju.edu.erp.model.po.SaleSheetPO;
import com.nju.edu.erp.model.vo.sale.SaleSheetVO;

import java.math.BigDecimal;
import java.util.List;

public class LevelDiscountStrategy implements PromotionStrategy {
    private final BigDecimal additionalDiscount;

    public LevelDiscountStrategy(BigDecimal additionalDiscount) {
        this.additionalDiscount = additionalDiscount;
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
        resVO.setDiscount(resVO.getDiscount().add(additionalDiscount));
    }
}
