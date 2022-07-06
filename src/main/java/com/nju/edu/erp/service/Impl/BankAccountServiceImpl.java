package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.BankAccountDao;
import com.nju.edu.erp.model.po.BankAccountPO;
import com.nju.edu.erp.model.vo.BankAccountVO;
import com.nju.edu.erp.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountDao BankAccountDao;

    @Autowired
    public BankAccountServiceImpl(BankAccountDao BankAccountDao) {
        this.BankAccountDao = BankAccountDao;
    }

    /**
     * 根据payment与receipt表单更新银行账户信息
     * @param bankAccountPO 银行账户信息
     */
    @Override
    public void updateBankAccount(BankAccountPO bankAccountPO) {
        BankAccountDao.updateOne(bankAccountPO);
    }

    @Override
    public void updateBankAccount(BankAccountVO bankAccountVO) {
        BankAccountPO bankAccountPO=new BankAccountPO(bankAccountVO.getId(),bankAccountVO.getName(),bankAccountVO.getRemaining_sum());
        BankAccountDao.updateOne(bankAccountPO);
    }

    /**
     * 查找所有银行账户
     * @return 银行账户列表
     */
    @Override
    public List<BankAccountPO> getBankAccounts() {

        return BankAccountDao.findAll();
    }

    /**
     * insert一个用户
     * @param vo
     * @return 正常情况返回影响行数，0代表失败，1代表成功
     */
    @Override
    public int insertBankAccount(BankAccountVO vo) {
        try{
            BankAccountPO BankAccountPO = new BankAccountPO(vo.getId(),vo.getName(),vo.getRemaining_sum());
            return BankAccountDao.insertBankAccount(BankAccountPO);
        }catch (Exception e){
            return 0;
        }
    }

    /**
     * delete一个银行账户
     * @param vo
     * @return 影响的行数，若id无匹配返回0，因为不太会报错所以不使用try&catch
     */
    @Override
    public int deleteBankAccount(BankAccountVO vo) {
        BankAccountPO BankAccountPO = new BankAccountPO(vo.getId(),vo.getName(),vo.getRemaining_sum());
        return BankAccountDao.deleteBankAccount(BankAccountPO);
    }

    /**
     * 查找一名用户
     * @param name
     * @return BankAccountPO
     */
    @Override
    public BankAccountPO findBankAccountByName(String name) {
        return BankAccountDao.findOneByName(name);
    }
}
