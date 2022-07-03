package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.*;
import com.nju.edu.erp.enums.sheetState.SaleReturnsSheetState;
import com.nju.edu.erp.enums.sheetState.SaleSheetState;
import com.nju.edu.erp.model.po.*;
import com.nju.edu.erp.model.vo.SaleReturns.SaleReturnsSheetContentVO;
import com.nju.edu.erp.model.vo.SaleReturns.SaleReturnsSheetVO;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.service.CustomerService;
import com.nju.edu.erp.service.ProductService;
import com.nju.edu.erp.service.SaleReturnsService;
import com.nju.edu.erp.service.WarehouseService;
import com.nju.edu.erp.utils.IdGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class SaleReturnsServiceImpl implements SaleReturnsService {

    SaleReturnsSheetDao saleReturnsSheetDao;

    ProductService productService;

    ProductDao productDao;

    SaleSheetDao saleSheetDao;

    CustomerService customerService;

    WarehouseService warehouseService;

    WarehouseDao warehouseDao;

    WarehouseOutputSheetDao warehouseOutputSheetDao;

    @Autowired
    public SaleReturnsServiceImpl(SaleReturnsSheetDao saleReturnsSheetDao, ProductService productService, ProductDao productDao, SaleSheetDao saleSheetDao, CustomerService customerService, WarehouseService warehouseService, WarehouseDao warehouseDao, WarehouseOutputSheetDao warehouseOutputSheetDao) {
        this.saleReturnsSheetDao = saleReturnsSheetDao;

        this.productService = productService;

        this.productDao = productDao;

        this.saleSheetDao = saleSheetDao;

        this.customerService = customerService;

        this.warehouseService = warehouseService;

        this.warehouseDao = warehouseDao;

        this.warehouseOutputSheetDao = warehouseOutputSheetDao;
    }

    /**
     * 制定销售退货单
     *
     * @param saleReturnsSheetVO 销售退货单
     */
    @Override
    @Transactional
    public void makeSaleReturnsSheet(UserVO userVO, SaleReturnsSheetVO saleReturnsSheetVO) {
        SaleReturnsSheetPO saleReturnsSheetPO = new SaleReturnsSheetPO();
        BeanUtils.copyProperties(saleReturnsSheetVO, saleReturnsSheetPO);

        saleReturnsSheetPO.setOperator(userVO.getName());
        saleReturnsSheetPO.setCreateTime(new Date());
        SaleReturnsSheetPO latest = saleReturnsSheetDao.getLatest();
        String id = IdGenerator.generateSheetId(latest == null ? null : latest.getId(), "XSTHD");
        saleReturnsSheetPO.setSaleSheetId(id);
        saleReturnsSheetPO.setState(SaleSheetState.PENDING_LEVEL_1);
        BigDecimal finalAmount = BigDecimal.ZERO;
        SaleSheetPO saleSheet = saleSheetDao.findSheetById(saleReturnsSheetVO.getSaleSheetId());

        // 每件商品的实际折扣率 = 折扣 - 消费券 / 订单总价
        BigDecimal discount = saleSheet.getDiscount();
        BigDecimal voucher = saleSheet.getVoucherAmount();
        BigDecimal rawTotalAmount = saleSheet.getRawTotalAmount();
        BigDecimal trueDiscount = discount.subtract(voucher.divide(rawTotalAmount, voucher.scale() - rawTotalAmount.scale(), RoundingMode.FLOOR));

        List<SaleSheetContentPO> saleSheetContents = saleSheetDao.findContentBySheetId(saleReturnsSheetPO.getSaleSheetId());
        Map<String, SaleSheetContentPO> map = new HashMap<>();
        for (SaleSheetContentPO item : saleSheetContents) {
            map.put(item.getPid(), item);
        }
        List<SaleReturnsSheetContentPO> pContentPOList = new ArrayList<>();
        for (SaleReturnsSheetContentVO content : saleReturnsSheetVO.getSaleReturnsSheetContent()) {
            SaleReturnsSheetContentPO pContentPO = new SaleReturnsSheetContentPO();
            BeanUtils.copyProperties(content, pContentPO);
            pContentPO.setSaleReturnsSheetId(id);
            SaleSheetContentPO item = map.get(pContentPO.getPid());
            pContentPO.setUnitPrice(item.getUnitPrice());

            BigDecimal unitPrice = pContentPO.getUnitPrice();
            pContentPO.setTotalPrice(unitPrice.multiply(BigDecimal.valueOf(pContentPO.getQuantity())));
            pContentPOList.add(pContentPO);
            //rawTotalAmount = rawTotalAmount.add(pContentPO.getTotalPrice());
            finalAmount = finalAmount.add(pContentPO.getTotalPrice().multiply(trueDiscount));
        }
        saleReturnsSheetDao.saveBatch(pContentPOList);
        saleReturnsSheetPO.setFinalAmount(finalAmount);
        saleReturnsSheetDao.save(saleReturnsSheetPO);
    }

    /**
     * 根据状态获取销售退货单(state == null 则获取所有销售退货单)
     *
     * @param state 销售退货单状态
     * @return 销售退货单
     */
    @Override
    public List<SaleReturnsSheetVO> getSaleReturnsSheetByState(SaleReturnsSheetState state) {
        List<SaleReturnsSheetVO> res = new ArrayList<>();
        List<SaleReturnsSheetPO> all;
        if (state == null) {
            all = saleReturnsSheetDao.findAll();
        } else {
            all = saleReturnsSheetDao.findAllByState(state);
        }
        for (SaleReturnsSheetPO po : all) {
            SaleReturnsSheetVO vo = new SaleReturnsSheetVO();
            BeanUtils.copyProperties(po, vo);
            List<SaleReturnsSheetContentPO> alll = saleReturnsSheetDao.findContentBySaleReturnsSheetId(po.getId());
            List<SaleReturnsSheetContentVO> vos = new ArrayList<>();
            for (SaleReturnsSheetContentPO p : alll) {
                SaleReturnsSheetContentVO v = new SaleReturnsSheetContentVO();
                BeanUtils.copyProperties(p, v);
                vos.add(v);
            }
            vo.setSaleReturnsSheetContent(vos);
            res.add(vo);
        }
        return res;
    }

    /**
     * 根据销售退货单id进行审批(state == "待二级审批"/"审批完成"/"审批失败")
     * 在controller层进行权限控制
     *
     * @param saleReturnsSheetId 进货退货单id
     * @param state              进货退货单修改后的状态
     */
    @Override
    @Transactional
    public void approval(String saleReturnsSheetId, SaleReturnsSheetState state) {
        SaleReturnsSheetPO saleReturnsSheet = saleReturnsSheetDao.findOneById(saleReturnsSheetId);
        if (state.equals(SaleReturnsSheetState.FAILURE)) {
            if (saleReturnsSheet.getState().equals(SaleSheetState.SUCCESS)) throw new RuntimeException("状态更新失败");
            int effectLines = saleReturnsSheetDao.updateState(saleReturnsSheetId, state);
            if (effectLines == 0) throw new RuntimeException("状态更新失败");
        } else {
            SaleReturnsSheetState prevState;
            if (state.equals(SaleReturnsSheetState.SUCCESS)) {
                prevState = SaleReturnsSheetState.PENDING_LEVEL_2;
            } else if (state.equals(SaleReturnsSheetState.PENDING_LEVEL_2)) {
                prevState = SaleReturnsSheetState.PENDING_LEVEL_1;
            } else {
                throw new RuntimeException("状态更新失败");
            }
            int effectLines = saleReturnsSheetDao.updateStateV2(saleReturnsSheetId, prevState, state);
            if (effectLines == 0) throw new RuntimeException("状态更新失败");
            if (state.equals(SaleReturnsSheetState.SUCCESS)) {
                // 销售退货单Id -> 销售单id -> 出库单id -> 出库细节（(批号, 数量)）

                String saleSheetId = saleReturnsSheet.getSaleSheetId();
                String warehouseOutputSheetId = warehouseOutputSheetDao.getOutputSheetIdByPSSheetId(saleSheetId);

                // the quantity of the elements in outputContents would be modified for .
                List<WarehouseOutputSheetContentPO> outputContents = warehouseOutputSheetDao.getAllContentById(warehouseOutputSheetId);
                List<SaleReturnsSheetContentPO> sheetContents = saleReturnsSheetDao.findContentBySaleReturnsSheetId(saleReturnsSheetId);
                for (SaleReturnsSheetContentPO content : sheetContents) {
                    String pid = content.getPid();
                    Integer quantity = content.getQuantity();

                    for (WarehouseOutputSheetContentPO outputContent : outputContents) {
                        if (outputContent.getPid().equals(pid)) {
                            if (outputContent.getQuantity() >= quantity) {
                                outputContent.setQuantity(outputContent.getQuantity() - quantity);
                                WarehousePO increment = warehouseDao.findOneByPidAndBatchId(pid, outputContent.getBatchId());
                                if (increment == null) throw new RuntimeException("单据发生错误！请联系管理员！");
                                increment.setQuantity(quantity);
                                warehouseDao.increaseQuantity(increment);
                                quantity = 0;
                                break;
                            } else {
                                quantity -= outputContent.getQuantity();
                                WarehousePO increment = warehouseDao.findOneByPidAndBatchId(pid, outputContent.getBatchId());
                                if (increment == null) throw new RuntimeException("单据发生错误！请联系管理员！");
                                increment.setQuantity(outputContent.getQuantity());
                                warehouseDao.increaseQuantity(increment);
                                outputContent.setQuantity(0);
                            }
                        }
                    }

                    if (quantity > 0) {
                        saleReturnsSheetDao.updateState(saleReturnsSheetId, SaleReturnsSheetState.FAILURE);
                        throw new RuntimeException("退货数量大于购买数量！审批失败！");
                    }
                }
                Integer customerId = saleSheetDao.findSheetById(saleSheetId).getSupplier();
                CustomerPO customer = customerService.findCustomerById(customerId);
                customer.setReceivable(customer.getReceivable().subtract(saleReturnsSheet.getFinalAmount()));
                customerService.updateCustomer(customer);
            }
        }
    }
}
