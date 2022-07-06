package com.nju.edu.erp.dao;

import com.nju.edu.erp.model.po.BankAccountPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface BankAccountDao {
    int updateOne(BankAccountPO bankAccountPO);

    BankAccountPO findOneByName(String name);

    int insertBankAccount(BankAccountPO bankAccountPO);

    int deleteBankAccount(BankAccountPO bankAccountPO);

    List<BankAccountPO> findAll();
}
