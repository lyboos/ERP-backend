package com.nju.edu.erp.service.Impl.promotionStrategy;

import com.nju.edu.erp.model.po.SaleSheetContentPO;
import com.nju.edu.erp.model.po.SaleSheetPO;
import com.nju.edu.erp.model.vo.sale.SaleSheetVO;

import java.util.List;

public interface PromotionStrategy {
    /**
     * 在生成PO内容前对前端传入的VO进行预处理，不修改原VO内容，
     * 而是用调用者生成的一个新vo一个新的修改过的VO。比如一定比例会提高代金券、折让率、满减等
     * @param resVO 调用者保证resVO被saleSheetVO初始化过
     */
    default void preProcessVO(SaleSheetVO resVO, SaleSheetVO calVO, SaleSheetPO calPO, List<SaleSheetContentPO> calPOList) {
    }

    /**
     * 在生成PO内容后对被后端逻辑处理过，已经确定最终内容（比如最终本单价格）但是还没进入数据库的PO不修改，
     * 生成一个新的PO。可以实现一定情况下赠送商品等。
     * @param resPO 调用者保证resPO被saleSheetPO初始化过
     */
    default void postProcessPO(SaleSheetPO resPO, List<SaleSheetContentPO> resList, SaleSheetVO originVO, SaleSheetPO calPO, List<SaleSheetContentPO> calPOList) {
    }
}
