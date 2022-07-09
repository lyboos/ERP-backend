package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.model.vo.businessHistory.BusinessHistoryRequestVO;
import com.nju.edu.erp.service.BusinessHistoryService;
import com.nju.edu.erp.utils.DateUtils;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

;

@RestController
@RequestMapping(path = "/check-bh")
public class BusinessHistoryController {
    private final BusinessHistoryService BusinessHistorySheetService;

    @Autowired
    public BusinessHistoryController(BusinessHistoryService BusinessHistoryService) {
        this.BusinessHistorySheetService= BusinessHistoryService;
    }


    @Authorized (roles = {Role.FINANCIAL_STAFF, Role.GM, Role.ADMIN})
    @GetMapping(value = "/find-sheets")
    public Response findByRequest(@RequestParam("startTime") String startTime,
                                  @RequestParam("endTime") String endTime,
                                  @RequestParam("sheetType") String sheetType,
                                  @RequestParam("customerName") String customerName,
                                  @RequestParam("operatorName") String operatorName
    )  {
        BusinessHistoryRequestVO requestVO=new BusinessHistoryRequestVO();
        requestVO.setStartTime(DateUtils.transfer_to_date(startTime));
        requestVO.setEndTime(DateUtils.transfer_to_date(endTime));
        requestVO.setSheetType(Integer.parseInt(sheetType));
        requestVO.setCustomerName(customerName);
        requestVO.setOperatorName(operatorName);
        return Response.buildSuccess(BusinessHistorySheetService.request(requestVO));
    }

}