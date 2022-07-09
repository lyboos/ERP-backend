package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.model.vo.Payment.PaymentVO;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.service.SaleService;
import com.nju.edu.erp.utils.BigDecimalsArrayUtil;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping(path = "/strategy")
public class SaleStrategyController {
    private final SaleService SaleService;

    @Autowired
    public SaleStrategyController(SaleService saleService) {
        this.SaleService = saleService;
    }

    /**
     * 对不同等级客户实行促销
     */
    @Authorized(roles = {Role.GM, Role.ADMIN})
    @PostMapping(value = "/user-level")
    public Response user_level(@RequestParam(value = "level") String level,@RequestParam(value = "discount") String discount)  {
        SaleService.getUserStrategy(BigDecimalsArrayUtil.getBigDecimals(level,discount));
        return Response.buildSuccess();
    }

    /**
     * 对一定额度金额实行促销
     */
    @Authorized(roles = {Role.GM, Role.ADMIN})
    @PostMapping(value = "/additional")
    public Response additional(@RequestParam(value = "additionalDiscount") String additionalDiscount,@RequestParam(value = "trigger") String trigger)  {
        SaleService.getAdditionalDiscountStrategy(new BigDecimal(additionalDiscount),new BigDecimal(trigger));
        return Response.buildSuccess();
    }

    /**
     * 对一定额度金额赠送商品
     */
    @Authorized(roles = {Role.GM, Role.ADMIN})
    @PostMapping(value = "/give-away")
    public Response give_away(@RequestParam(value = "trigger") String trigger,@RequestParam(value = "numOfGiveaway") String numOfGiveaway)  {
        SaleService.getGiveawayStrategy(new BigDecimal(trigger), Integer.valueOf(numOfGiveaway));
        return Response.buildSuccess();
    }

}
