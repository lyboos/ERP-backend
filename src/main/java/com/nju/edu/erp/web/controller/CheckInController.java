package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.model.vo.CheckInVO;
import com.nju.edu.erp.service.CheckInService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/check-in")
public class CheckInController {
    private final CheckInService CheckInService;

    @Autowired
    public CheckInController(CheckInService checkInService) {
        this.CheckInService = checkInService;
    }

    /**
     * 员工打卡
     */

    @Authorized(roles = {Role.FINANCIAL_STAFF,
            Role.INVENTORY_MANAGER,
            Role.SALE_MANAGER,
            Role.SALE_STAFF,
            Role.HR,
            Role.ADMIN})
    @GetMapping("/checkin")
    public Response CheckIn(@RequestParam(value = "id") String name){
        System.out.println("has triggered controller"+name);
        CheckInService.checkIn(name);
        return Response.buildSuccess();
    }

}
