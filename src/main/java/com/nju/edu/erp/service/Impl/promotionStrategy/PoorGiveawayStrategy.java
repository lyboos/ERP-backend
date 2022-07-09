package com.nju.edu.erp.service.Impl.promotionStrategy;

import com.nju.edu.erp.model.po.SaleSheetContentPO;
import com.nju.edu.erp.model.po.SaleSheetPO;
import com.nju.edu.erp.model.vo.sale.SaleSheetVO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

public class PoorGiveawayStrategy implements PromotionStrategy {
    // 初始价格超出一定价格，随机一项商品多送n个
    private final BigDecimal trigger;
    private final Integer n;

    public PoorGiveawayStrategy(BigDecimal trigger, Integer n) {
        this.trigger = trigger;
        this.n = n;
    }

    @Override
    public void postProcessPO(SaleSheetPO resPO, List<SaleSheetContentPO> resList, SaleSheetVO originVO, SaleSheetPO calPO, List<SaleSheetContentPO> calPOList) {
        if (calPO.getRawTotalAmount().compareTo(trigger) >= 0) {
            Random r = new Random();
            r.setSeed(114514);
            int i = r.nextInt();
            i = i % resList.size();
            SaleSheetContentPO contentPO = resList.get(i);
            contentPO.setQuantity(contentPO.getQuantity() + n);
        }
    }
}
