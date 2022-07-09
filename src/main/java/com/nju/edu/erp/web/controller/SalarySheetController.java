package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.enums.sheetState.SalarySheetState;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.SalarySheetVO;
import com.nju.edu.erp.service.SalarySheetService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/salary")
public class SalarySheetController {

    private final SalarySheetService SalaryService;

    @Autowired
    public SalarySheetController(SalarySheetService salaryService) {
        this.SalaryService = salaryService;
    }

    /**
     * 销售人员制定工资单
     */
    @Authorized (roles = {Role.SALE_STAFF, Role.SALE_MANAGER, Role.GM, Role.ADMIN})
    @PostMapping(value = "/sheet-make")
    public Response makeSalaryOrder(UserVO userVO, @RequestBody SalarySheetVO SalarySheetVO)  {
        SalaryService.makeSheet(userVO, SalarySheetVO);
        return Response.buildSuccess();
    }

    /**
     * HR审批
     * @param SalarySheetId 工资单id
     * @param state 修改后的状态("审批失败"/"待二级审批")
     */
    @GetMapping(value = "/first-approval")
    @Authorized (roles = {Role.SALE_MANAGER, Role.GM, Role.ADMIN})
    public Response firstApproval(@RequestParam("SalarySheetId") String SalarySheetId,
                                  @RequestParam("state") SalarySheetState state)  {
        if(state.equals(SalarySheetState.FAILURE) || state.equals(SalarySheetState.PENDING_LEVEL_2)) {
            SalaryService.approval(SalarySheetId, state);
            return Response.buildSuccess();
        } else {
            return Response.buildFailed("000000","操作失败"); // code可能得改一个其他的
        }
    }

    /**
     * 总经理审批
     * @param SalarySheetId 工资单id
     * @param state 修改后的状态("审批失败"/"审批完成")
     */
    @Authorized (roles = {Role.GM, Role.ADMIN})
    @GetMapping(value = "/second-approval")
    public Response secondApproval(@RequestParam("sheetId") String SalarySheetId,
                                   @RequestParam("state") SalarySheetState state)  {
        if(state.equals(SalarySheetState.FAILURE) || state.equals(SalarySheetState.SUCCESS)) {
            SalaryService.approval(SalarySheetId, state);
            return Response.buildSuccess();
        } else {
            return Response.buildFailed("000000","操作失败"); // code可能得改一个其他的
        }
    }

    /**
     * 根据状态查看工资单
     */
    @GetMapping(value = "/sheet-show")
    public Response showSheetByState(@RequestParam(value = "state", required = false) SalarySheetState state)  {
        return Response.buildSuccess(SalaryService.getSheetByState(state));
    }


    /**
     * 根据工资单Id搜索工资单信息
     * @param id 工资单Id
     * @return 工资单全部信息
     */
    @GetMapping(value = "/find-sheet")
    public Response findBySheetId(@RequestParam(value = "id") String id)  {
        return Response.buildSuccess(SalaryService.getSheetById(id));
    }

}
