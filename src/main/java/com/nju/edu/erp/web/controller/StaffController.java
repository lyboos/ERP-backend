package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.StaffInfoVO;
import com.nju.edu.erp.service.StaffService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/staff")
public class StaffController {
    private final StaffService staffService;

    @Autowired
    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    /**
     * 新员工登记
     */
    @Authorized(roles = {Role.HR, Role.GM, Role.ADMIN})
    @PostMapping(value = "/staff-create")
    public Response makeStaffOrder(@RequestBody StaffInfoVO StaffInfoVO)  {
        staffService.createNewStaff(StaffInfoVO);
        return Response.buildSuccess();
    }

}
