package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.ReceiptDao;
import com.nju.edu.erp.enums.sheetState.ReceiptState;
import com.nju.edu.erp.model.po.CustomerPO;
import com.nju.edu.erp.model.po.ReceiptContentPO;
import com.nju.edu.erp.model.po.ReceiptPO;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.receipt.ReceiptContentVO;
import com.nju.edu.erp.model.vo.receipt.ReceiptVO;
import com.nju.edu.erp.service.CustomerService;
import com.nju.edu.erp.service.ReceiptService;
import com.nju.edu.erp.utils.IdGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// 面向接口编程。详见父接口
@Service
public class ReceiptServiceImpl implements ReceiptService {

    ReceiptDao receiptDao;

    CustomerService customerService;

    @Autowired
    public ReceiptServiceImpl(ReceiptDao receiptDao, CustomerService customerService) {
        this.receiptDao = receiptDao;
        this.customerService = customerService;
    }

    /**
     * 制定单
     *
     * @param userVO  用户VO
     * @param receiptVO 单VO
     */
    @Override
    @Transactional
    public void makeSheet(UserVO userVO, ReceiptVO receiptVO) {
        ReceiptPO receiptPO = new ReceiptPO();
        BeanUtils.copyProperties(receiptVO, receiptPO);

        receiptPO.setOperator(userVO.getName());
        receiptPO.setCreateTime(new Date());
        ReceiptPO latest = receiptDao.getLatest();
        String id = IdGenerator.generateSheetId(latest == null? null : latest.getId(), "SKD");
        receiptPO.setId(id);
        receiptPO.setState(ReceiptState.PENDING_LEVEL_2);
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<ReceiptContentPO> receiptContentPOList = new ArrayList<>();
        for (ReceiptContentVO contentVO: receiptVO.getSheetContent()) {
            ReceiptContentPO contentPO = new ReceiptContentPO();
            BeanUtils.copyProperties(contentVO, contentPO);
            contentPO.setSheetId(id);
            totalAmount = totalAmount.add(contentPO.getAmount());
            receiptContentPOList.add(contentPO);
        }
        receiptPO.setTotalAmount(totalAmount);
        receiptDao.saveBatch(receiptContentPOList);
        receiptDao.save(receiptPO);
    }

    /**
     * 根据状态获取单(state == null 则获取所有单)
     *
     * @param state 单状态
     * @return 单
     */
    @Override
    public List<ReceiptVO> getSheetByState(ReceiptState state) {
        List<ReceiptVO> res = new ArrayList<>();
        List<ReceiptPO> all;
        if (state == null) {
            all = receiptDao.findAll();
        } else {
            all = receiptDao.findAllByState(state);
        }
        for (ReceiptPO po: all) {
            ReceiptVO vo = new ReceiptVO();
            BeanUtils.copyProperties(po, vo);
            List<ReceiptContentPO> alll = receiptDao.findContentBySheetId(po.getId());
            copyContentFromPOContent(vo, alll);
            res.add(vo);
        }
        return res;
    }

    private void copyContentFromPOContent(ReceiptVO vo, List<ReceiptContentPO> list) {
        List<ReceiptContentVO> vos = new ArrayList<>();
        for (ReceiptContentPO p: list) {
            ReceiptContentVO v = new ReceiptContentVO();
            BeanUtils.copyProperties(p, v);
            vos.add(v);
        }
        vo.setSheetContent(vos);
    }

    /**
     * 根据单id进行审批(state), 注意，只需要2级审批和审批完成
     * 在controller层进行权限控制
     *
     * @param sheetId      单id
     * @param state 单修改后的状态
     */
    @Override
    @Transactional
    public void approval(String sheetId, ReceiptState state) {
        if (state.equals(ReceiptState.FAILURE)) {
            ReceiptPO sheet = receiptDao.findSheetById(sheetId);
            if (sheet.getState().equals(ReceiptState.SUCCESS)) throw new RuntimeException("状态更新失败");
            int effectLines = receiptDao.updateState(sheetId, state);
            if (effectLines == 0) throw new RuntimeException("状态更新失败");
        } else {
            ReceiptState prevState;
            if (state.equals(ReceiptState.SUCCESS)) {
                prevState = ReceiptState.PENDING_LEVEL_2;
            } else {
                throw new RuntimeException("状态更新失败");
            }
            int effectLines = receiptDao.updateStateV2(sheetId, prevState, state);
            if (effectLines == 0) throw new RuntimeException("状态更新失败");
            if (state.equals(ReceiptState.SUCCESS)) {
                // 更新客户表的应收字段receivable
                ReceiptPO receiptPO = receiptDao.findSheetById(sheetId);
                CustomerPO customerPO = customerService.findCustomerById(receiptPO.getSupplier());
                customerPO.setReceivable(customerPO.getReceivable().subtract(receiptPO.getTotalAmount()));
                customerService.updateCustomer(customerPO);
            }
        }
    }

    /**
     * 根据单Id搜索进货单信息
     *
     * @param sheetId 单Id
     * @return 单全部信息
     */
    @Override
    public ReceiptVO getSheetById(String sheetId) {
        ReceiptPO receiptPO = receiptDao.findSheetById(sheetId);
        if (receiptPO == null) return null;
        List<ReceiptContentPO> contentPO = receiptDao.findContentBySheetId(sheetId);
        ReceiptVO vo = new ReceiptVO();
        BeanUtils.copyProperties(receiptPO, vo);
        copyContentFromPOContent(vo, contentPO);
        return vo;
    }
}
