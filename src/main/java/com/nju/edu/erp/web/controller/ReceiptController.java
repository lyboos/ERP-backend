package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.enums.sheetState.RandPState;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.receipt.ReceiptVO;
import com.nju.edu.erp.service.ReceiptService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/proceeds")
public class ReceiptController {
    private final ReceiptService ReceiptService;

    @Autowired
    public ReceiptController(ReceiptService ReceiptService) {
        this.ReceiptService = ReceiptService;
    }

    /**
     * 财务人员制定收款单
     */
    @Authorized (roles = {Role.FINANCIAL_STAFF, Role.GM, Role.ADMIN})
    @PostMapping(value = "/sheet-make")
    public Response makePurchaseOrder(UserVO userVO, @RequestBody ReceiptVO ReceiptVO)  {
        ReceiptService.makeSheet(userVO, ReceiptVO);
        return Response.buildSuccess();
    }
    /**
     * 根据状态查看收款单
     */
    @Authorized(roles = {Role.FINANCIAL_STAFF,Role.ADMIN})
    @GetMapping("/sheet-show")
    public Response findReceiptByState(@RequestParam(value = "state", required = false) RandPState state) {
        return Response.buildSuccess(ReceiptService.getSheetByState(state));
    }


    /**
     * 总经理审批
     * @param ReceiptSheetId 收款单id
     * @param state 修改后的状态("审批失败"/"审批完成")
     */
    @Authorized (roles = {Role.GM, Role.ADMIN})
    @GetMapping(value = "/second-approval")
    public Response secondApproval(@RequestParam("ReceiptSheetId") String ReceiptSheetId,
                                   @RequestParam("state") RandPState state)  {
        if(state.equals(RandPState.FAILURE) || state.equals(RandPState.SUCCESS)) {
            ReceiptService.approval(ReceiptSheetId, state);
            return Response.buildSuccess();
        } else {
            return Response.buildFailed("000000","操作失败"); // code可能得改一个其他的
        }
    }

}
