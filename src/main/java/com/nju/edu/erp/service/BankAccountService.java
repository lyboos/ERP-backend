package com.nju.edu.erp.service;

import com.nju.edu.erp.model.po.BankAccountPO;
import com.nju.edu.erp.model.vo.BankAccountVO;

import java.util.List;

public interface BankAccountService {
    /**
     * 根据payment与receipt表单更新银行账户信息
     * @param bankAccountPO 银行账户信息
     */
    void updateBankAccount(BankAccountPO bankAccountPO);

    /**
     * 根据姓名更新银行账户信息
     * @param bankAccountVO 银行账户信息
     */
    void updateBankAccount(BankAccountVO bankAccountVO);

    /**
     * 查找所有银行账户
     * @return 银行账户列表
     */
    List<BankAccountPO> getBankAccounts();

    /**
     * 新增一名银行账户
     * @param bankAccountVO
     * @return int
     */
    int insertBankAccount(BankAccountVO bankAccountVO);

    /**
     * 删除一名银行账户
     * @param bankAccountVO
     * @return int
     */
    int deleteBankAccount(BankAccountVO bankAccountVO);

    /**
     * 查找一名用户
     * @param name
     * @return BankAccountPO
     */
    BankAccountPO findBankAccountByName(String name);
}
