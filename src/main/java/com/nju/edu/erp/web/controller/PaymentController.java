package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.enums.sheetState.RandPState;
import com.nju.edu.erp.model.vo.Payment.PaymentVO;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.service.PaymentService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/pay")
public class PaymentController {
    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * 财务人员制定付款单
     */
    @Authorized (roles = {Role.FINANCIAL_STAFF, Role.GM, Role.ADMIN})
    @PostMapping(value = "/sheet-make")
    public Response makePurchaseOrder(UserVO userVO, @RequestBody PaymentVO paymentVO)  {
        paymentService.makeSheet(userVO, paymentVO);
        return Response.buildSuccess();
    }
    /**
     * 根据状态查看付款单
     */
    @Authorized(roles = {Role.FINANCIAL_STAFF,Role.ADMIN})
    @GetMapping("/sheet-show")
    public Response findPaymentByState(@RequestParam(value = "state", required = false) RandPState state) {
        return Response.buildSuccess(paymentService.getSheetByState(state));
    }


    /**
     * 总经理审批
     * @param paymentSheetId 付款单id
     * @param state 修改后的状态("审批失败"/"审批完成")
     */
    @Authorized (roles = {Role.GM, Role.ADMIN})
    @GetMapping(value = "/second-approval")
    public Response secondApproval(@RequestParam("paymentSheetId") String paymentSheetId,
                                   @RequestParam("state") RandPState state)  {
        if(state.equals(RandPState.FAILURE) || state.equals(RandPState.SUCCESS)) {
            paymentService.approval(paymentSheetId, state);
            return Response.buildSuccess();
        } else {
            return Response.buildFailed("000000","操作失败"); // code可能得改一个其他的
        }
    }

}
