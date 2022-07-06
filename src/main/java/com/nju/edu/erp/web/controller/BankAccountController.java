package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.model.vo.BankAccountVO;
import com.nju.edu.erp.service.BankAccountService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/bank-account")
public class BankAccountController {
    private final BankAccountService BankAccountService;

    @Autowired
    public BankAccountController(BankAccountService BankAccountService) {
        this.BankAccountService = BankAccountService;
    }

    @Authorized(roles = {Role.ADMIN})
    @GetMapping("/account-show")
    public Response findAll() {
        return Response.buildSuccess(BankAccountService.getBankAccounts());
    }

    @Authorized(roles = {Role.ADMIN})
    @PostMapping("/account-create")
    public Response insertBankAccount(@RequestBody BankAccountVO bankAccountVO){
        System.out.println("has triggered controller"+bankAccountVO.getName());
        BankAccountService.insertBankAccount(bankAccountVO);
        return Response.buildSuccess();
    }

    @Authorized(roles = {Role.ADMIN})
    @PostMapping("/account-delete")
    public Response deleteBankAccount(@RequestBody BankAccountVO bankAccountVO){
        System.out.println("has triggered controller"+bankAccountVO.getName());
        BankAccountService.deleteBankAccount(bankAccountVO);
        return Response.buildSuccess();
    }

    @Authorized(roles = {Role.ADMIN})
    @PostMapping("/account-update")
    public Response updateBankAccount(@RequestBody BankAccountVO bankAccountVO){
        System.out.println("has triggered controller"+bankAccountVO.getName());
        BankAccountService.updateBankAccount(bankAccountVO);
        return Response.buildSuccess();
    }

    @Authorized(roles = {Role.ADMIN})
    @PostMapping("/findByKeyword")
    public Response findBankAccount(@RequestBody String name){
        System.out.println("has triggered controller"+name);
        BankAccountService.findBankAccountByName(name);
        return Response.buildSuccess();
    }
}
