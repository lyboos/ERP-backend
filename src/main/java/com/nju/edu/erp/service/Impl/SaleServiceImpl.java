package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.SaleSheetDao;
import com.nju.edu.erp.enums.sheetState.SaleSheetState;
import com.nju.edu.erp.model.po.*;
import com.nju.edu.erp.model.vo.ProductInfoVO;
import com.nju.edu.erp.model.vo.sale.SaleSheetContentVO;
import com.nju.edu.erp.model.vo.sale.SaleSheetVO;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.warehouse.WarehouseOutputFormContentVO;
import com.nju.edu.erp.model.vo.warehouse.WarehouseOutputFormVO;
import com.nju.edu.erp.service.CustomerService;
import com.nju.edu.erp.service.Impl.promotionStrategy.AdditionalDiscountStrategy;
import com.nju.edu.erp.service.Impl.promotionStrategy.PoorGiveawayStrategy;
import com.nju.edu.erp.service.Impl.promotionStrategy.LevelDiscountStrategy;
import com.nju.edu.erp.service.Impl.promotionStrategy.PromotionStrategy;
import com.nju.edu.erp.service.ProductService;
import com.nju.edu.erp.service.SaleService;
import com.nju.edu.erp.service.WarehouseService;
import com.nju.edu.erp.utils.IdGenerator;
import jdk.internal.net.http.common.Pair;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaleServiceImpl implements SaleService {

    private final SaleSheetDao saleSheetDao;

    private final ProductService productService;

    private final CustomerService customerService;

    private final WarehouseService warehouseService;

    // 处理顺序在makeSheet方法里定义
    @Setter
    private PromotionStrategy userStrategy = new LevelDiscountStrategy(BigDecimal.valueOf(0.01));

    @Setter
    private PromotionStrategy additionalDiscountStrategy = new AdditionalDiscountStrategy(BigDecimal.ZERO, BigDecimal.ZERO);

    @Setter
    private PromotionStrategy giveawayStrategy = new PoorGiveawayStrategy(BigDecimal.valueOf(1414180));

    @Autowired
    public SaleServiceImpl(SaleSheetDao saleSheetDao, ProductService productService, CustomerService customerService, WarehouseService warehouseService) {
        this.saleSheetDao = saleSheetDao;
        this.productService = productService;
        this.customerService = customerService;
        this.warehouseService = warehouseService;
    }

    @Override
    public BigDecimal getMonthAmountBySalesman(String salesman) {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        String month = df.format(date);
        return saleSheetDao.getMonthAmountBySalesman(salesman,month);
    }

    @Override
    @Transactional
    public void makeSaleSheet(UserVO userVO, SaleSheetVO saleSheetVO) {
        // TODO (done)
        // 需要持久化销售单（SaleSheet）和销售单content（SaleSheetContent），其中总价或者折后价格的计算需要在后端进行
        // 需要的service和dao层相关方法均已提供，可以不用自己再实现一遍
        // 预计算
        Pair<SaleSheetPO, List<SaleSheetContentPO>> p = cal(userVO, saleSheetVO);
        SaleSheetPO rawPO = p.first;
        List<SaleSheetContentPO> rawPOList = p.second;


        SaleSheetVO resVO = new SaleSheetVO();
        BeanUtils.copyProperties(rawPO, resVO);
        // resVO预处理，为真计算做准备
        userStrategy.preProcessVO(resVO, saleSheetVO, rawPO, rawPOList);
        additionalDiscountStrategy.preProcessVO(resVO, saleSheetVO, rawPO, rawPOList);
        giveawayStrategy.preProcessVO(resVO, saleSheetVO, rawPO, rawPOList);

        Pair<SaleSheetPO, List<SaleSheetContentPO>> processed = cal(userVO, resVO);
        SaleSheetPO resPO = processed.first;
        List<SaleSheetContentPO> resList = processed.second;
        // resPO和resPOList后置处理，赠送、减价等
        userStrategy.postProcessPO(resPO, resList, saleSheetVO, rawPO, rawPOList);
        additionalDiscountStrategy.postProcessPO(resPO, resList, saleSheetVO, rawPO, rawPOList);
        giveawayStrategy.postProcessPO(resPO, resList, saleSheetVO, rawPO, rawPOList);

        saleSheetDao.saveBatch(resList);
        BigDecimal finalAmount = resPO.getRawTotalAmount().multiply(resPO.getDiscount()).subtract(resPO.getVoucherAmount());
        resVO.setFinalAmount(finalAmount);
        saleSheetDao.save(resPO);
    }

    Pair<SaleSheetPO, List<SaleSheetContentPO>> cal(UserVO userVO, SaleSheetVO saleSheetVO) {
        SaleSheetPO saleSheetPO = new SaleSheetPO();
        BeanUtils.copyProperties(saleSheetVO, saleSheetPO);
        saleSheetPO.setOperator(userVO.getName());
        saleSheetPO.setCreateTime(new Date());
        SaleSheetPO latest = saleSheetDao.getLatest();
        String id = IdGenerator.generateSheetId(latest == null ? null : latest.getId(), "XSD");
        saleSheetPO.setId(id);
        saleSheetPO.setState(SaleSheetState.PENDING_LEVEL_1);
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<SaleSheetContentPO> sContentPOList = new ArrayList<>();
        for (SaleSheetContentVO content : saleSheetVO.getSaleSheetContent()) {
            SaleSheetContentPO sContentPO = new SaleSheetContentPO();
            BeanUtils.copyProperties(content, sContentPO);
            sContentPO.setSheetId(id);
            BigDecimal unitPrice = sContentPO.getUnitPrice();
            if (unitPrice == null) {
                ProductInfoVO product = productService.getOneProductByPid(content.getPid());
                unitPrice = product.getPurchasePrice();
                sContentPO.setUnitPrice(unitPrice);
            }
            sContentPO.setTotalPrice(unitPrice.multiply(BigDecimal.valueOf(sContentPO.getQuantity())));
            sContentPOList.add(sContentPO);
            totalAmount = totalAmount.add(sContentPO.getTotalPrice());
        }
        saleSheetPO.setRawTotalAmount(totalAmount);
        return new Pair<>(saleSheetPO, sContentPOList);
    }

    @Override
    @Transactional
    public List<SaleSheetVO> getSaleSheetByState(SaleSheetState state) {
        // TODO (done)
        // 根据单据状态获取销售单（注意：VO包含SaleSheetContent）
        // 依赖的dao层部分方法未提供，需要自己实现
        List<SaleSheetVO> res = new ArrayList<>();
        List<SaleSheetPO> all;
        if (state == null) {
            all = saleSheetDao.findAll();
        } else {
            all = saleSheetDao.findAll().stream().filter(po -> po.getState().equals(state)).collect(Collectors.toList());
        }
        for(SaleSheetPO po: all) {
            SaleSheetVO vo = new SaleSheetVO();
            BeanUtils.copyProperties(po, vo);
            List<SaleSheetContentPO> alll = saleSheetDao.findContentBySheetId(po.getId());
            List<SaleSheetContentVO> vos = new ArrayList<>();
            for (SaleSheetContentPO p: alll) {
                SaleSheetContentVO v = new SaleSheetContentVO();
                BeanUtils.copyProperties(p, v);
                vos.add(v);
            }
            vo.setSaleSheetContent(vos);
            res.add(vo);
        }
        return res;
    }

    /**
     * 根据销售单id进行审批(state == "待二级审批"/"审批完成"/"审批失败")
     * 在controller层进行权限控制
     *
     * @param saleSheetId 销售单id
     * @param state       销售单要达到的状态
     */
    @Override
    @Transactional
    public void approval(String saleSheetId, SaleSheetState state) {
        // TODO (done)
        // 需要的service和dao层相关方法均已提供，可以不用自己再实现一遍
        /* 一些注意点：
            1. 二级审批成功之后需要进行
                 1. 修改单据状态
                 2. 更新商品表
                 3. 更新客户表
                 4. 新建出库草稿
            2. 一级审批状态不能直接到审批完成状态； 二级审批状态不能回到一级审批状态
         */
        if (state.equals(SaleSheetState.FAILURE)) {
            SaleSheetPO saleSheet = saleSheetDao.findSheetById(saleSheetId);
            if (saleSheet.getState() == SaleSheetState.SUCCESS) throw new RuntimeException("状态更新失败");
            int effectLines = saleSheetDao.updateState(saleSheetId, state);
            if (effectLines == 0) throw new RuntimeException("状态更新失败");
        } else {
            SaleSheetState prevState;
            if (state.equals(SaleSheetState.SUCCESS)) {
                prevState = SaleSheetState.PENDING_LEVEL_2;
            } else if (state.equals(SaleSheetState.PENDING_LEVEL_2)) {
                prevState = SaleSheetState.PENDING_LEVEL_1;
            } else {
                throw new RuntimeException("状态更新失败");
            }
            int effectLines = saleSheetDao.updateStateV2(saleSheetId, prevState, state);
            if (effectLines == 0) throw new RuntimeException("状态更新失败");
            if (state.equals(SaleSheetState.SUCCESS)) {
                List<SaleSheetContentPO> saleSheetContent = saleSheetDao.findContentBySheetId(saleSheetId);
                List<WarehouseOutputFormContentVO> warehouseOutputFormContentVOS = new ArrayList<>();

                for (SaleSheetContentPO content: saleSheetContent) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    productInfoVO.setId(content.getPid());
                    productInfoVO.setRecentRp(content.getUnitPrice());
                    productService.updateProduct(productInfoVO);

                    WarehouseOutputFormContentVO woContentVO = new WarehouseOutputFormContentVO();
                    woContentVO.setSalePrice(content.getUnitPrice());
                    woContentVO.setQuantity(content.getQuantity());
                    woContentVO.setRemark(content.getRemark());
                    woContentVO.setPid(content.getPid());
                    warehouseOutputFormContentVOS.add(woContentVO);
                }

                SaleSheetPO saleSheet = saleSheetDao.findSheetById(saleSheetId);
                CustomerPO customerPO = customerService.findCustomerById(saleSheet.getSupplier());
                customerPO.setReceivable(customerPO.getReceivable().add(saleSheet.getFinalAmount()));
                customerService.updateCustomer(customerPO);

                WarehouseOutputFormVO warehouseOutputFormVO = new WarehouseOutputFormVO();
                warehouseOutputFormVO.setOperator(null);
                warehouseOutputFormVO.setSaleSheetId(saleSheetId);
                warehouseOutputFormVO.setList(warehouseOutputFormContentVOS);
                warehouseService.productOutOfWarehouse(warehouseOutputFormVO);
            }
        }
    }

    /**
     * 获取某个销售人员某段时间内消费总金额最大的客户(不考虑退货情况,销售单不需要审批通过,如果这样的客户有多个，仅保留一个)
     * @param salesman 销售人员的名字
     * @param beginDateStr 开始时间字符串
     * @param endDateStr 结束时间字符串
     * @return
     */
    public CustomerPurchaseAmountPO getMaxAmountCustomerOfSalesmanByTime(String salesman,String beginDateStr,String endDateStr){
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            Date beginTime =dateFormat.parse(beginDateStr);
            Date endTime=dateFormat.parse(endDateStr);
            if(beginTime.compareTo(endTime)>0){
                return null;
            }else{
                return saleSheetDao.getMaxAmountCustomerOfSalesmanByTime(salesman,beginTime,endTime);
            }
        }catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据销售单Id搜索销售单信息
     * @param saleSheetId 销售单Id
     * @return 销售单全部信息
     */
    @Override
    public SaleSheetVO getSaleSheetById(String saleSheetId) {
        SaleSheetPO saleSheetPO = saleSheetDao.findSheetById(saleSheetId);
        if(saleSheetPO == null) return null;
        List<SaleSheetContentPO> contentPO = saleSheetDao.findContentBySheetId(saleSheetId);
        SaleSheetVO sVO = new SaleSheetVO();
        BeanUtils.copyProperties(saleSheetPO, sVO);
        List<SaleSheetContentVO> saleSheetContentVOList = new ArrayList<>();
        for (SaleSheetContentPO content:
                contentPO) {
            SaleSheetContentVO sContentVO = new SaleSheetContentVO();
            BeanUtils.copyProperties(content, sContentVO);
            saleSheetContentVOList.add(sContentVO);
        }
        sVO.setSaleSheetContent(saleSheetContentVOList);
        return sVO;
    }
}
