package com.nju.edu.erp.service.Impl.promotionStrategy;

import com.nju.edu.erp.model.po.SaleSheetContentPO;
import com.nju.edu.erp.model.po.SaleSheetPO;
import com.nju.edu.erp.model.vo.sale.SaleSheetVO;

import java.util.List;

public class PipeStrategy implements PromotionStrategy {
    // 管道式处理，灵活，易修改
    private final PromotionStrategy[] strategies;

    public PipeStrategy(PromotionStrategy[] strategies) {
        this.strategies = strategies;
    }

    @Override
    public void preProcessVO(SaleSheetVO resVO, SaleSheetVO calVO, SaleSheetPO calPO, List<SaleSheetContentPO> calPOList) {
        for (PromotionStrategy strategy: strategies) {
            strategy.preProcessVO(resVO, calVO, calPO, calPOList);
        }
    }

    @Override
    public void postProcessPO(SaleSheetPO resPO, List<SaleSheetContentPO> resList, SaleSheetVO originVO, SaleSheetPO calPO, List<SaleSheetContentPO> calPOList) {
        for (PromotionStrategy strategy: strategies) {
            strategy.postProcessPO(resPO, resList, originVO, calPO, calPOList);
        }
    }
}
