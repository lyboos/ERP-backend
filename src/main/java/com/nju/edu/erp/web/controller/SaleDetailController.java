package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.model.vo.saleDetail.SaleDetailRequestVO;
import com.nju.edu.erp.service.SaleDetailSheetService;
import com.nju.edu.erp.utils.DateUtils;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/sale-detail")
public class SaleDetailController {
    private final SaleDetailSheetService saleDetailSheetService;

    @Autowired
    public SaleDetailController(SaleDetailSheetService saleDetailSheetService) {
        this.saleDetailSheetService= saleDetailSheetService;
    }


    @Authorized (roles = {Role.FINANCIAL_STAFF, Role.GM, Role.ADMIN})
    @GetMapping(value = "/find-sheets")
    public Response findByRequest(@RequestParam("startTime") String startTime,
                                  @RequestParam("endTime") String endTime,
                                  @RequestParam("productName") String productName,
                                  @RequestParam("customerName") String customerName,
                                  @RequestParam("operatorName") String operatorName
                                  )  {
        SaleDetailRequestVO requestVO=new SaleDetailRequestVO();
        requestVO.setStartTime(DateUtils.transfer_to_date(startTime));
        requestVO.setEndTime(DateUtils.transfer_to_date(endTime));
        requestVO.setProductName(productName);
        requestVO.setCustomerName(customerName);
        requestVO.setOperatorName(operatorName);
        return Response.buildSuccess(saleDetailSheetService.request(requestVO));
    }

}
