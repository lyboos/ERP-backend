package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.model.vo.businessCondition.BusinessConditionRequestVO;
import com.nju.edu.erp.service.BusinessConditionService;
import com.nju.edu.erp.utils.DateUtils;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

;

@RestController
@RequestMapping(path = "/check-bf")
public class BusinessConditionController {
    private final BusinessConditionService BusinessConditionSheetService;

    @Autowired
    public BusinessConditionController(BusinessConditionService businessConditionService) {
        this.BusinessConditionSheetService= businessConditionService;
    }


    @Authorized (roles = {Role.FINANCIAL_STAFF, Role.GM, Role.ADMIN})
    @GetMapping(value = "/find-sheets")
    public Response findByRequest(@RequestParam("startTime") String startTime,
                                  @RequestParam("endTime") String endTime
    )  {
        BusinessConditionRequestVO requestVO=new BusinessConditionRequestVO();
        requestVO.setStartTime(DateUtils.transfer_to_date(startTime));
        requestVO.setEndTime(DateUtils.transfer_to_date(endTime));
        return Response.buildSuccess(BusinessConditionSheetService.request(requestVO));
    }

}
