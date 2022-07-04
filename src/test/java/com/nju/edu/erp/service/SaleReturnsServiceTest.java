package com.nju.edu.erp.service;

import com.nju.edu.erp.dao.*;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.enums.sheetState.SaleReturnsSheetState;
import com.nju.edu.erp.enums.sheetState.SaleSheetState;
import com.nju.edu.erp.enums.sheetState.WarehouseOutputSheetState;
import com.nju.edu.erp.model.po.*;
import com.nju.edu.erp.model.vo.Sale.SaleSheetContentVO;
import com.nju.edu.erp.model.vo.Sale.SaleSheetVO;
import com.nju.edu.erp.model.vo.SaleReturns.SaleReturnsSheetContentVO;
import com.nju.edu.erp.model.vo.SaleReturns.SaleReturnsSheetVO;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.utils.IdGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

@SpringBootTest
public class SaleReturnsServiceTest { // 该测试为集成测试，需要用到数据库，请大家连给定的测试数据库进行测试

    @Autowired
    WarehouseService warehouseService;
    @Autowired
    SaleService saleService;

    @Autowired
    SaleSheetDao saleSheetDao;

    @Autowired
    ProductDao productDao;

    @Autowired
    CustomerDao customerDao;

    @Autowired
    WarehouseOutputSheetDao warehouseOutputSheetDao;

    @Autowired
    SaleReturnsSheetDao saleReturnsSheetDao;

    @Autowired
    SaleReturnsService saleReturnsService;

    @Autowired
    PurchaseReturnsService purchaseReturnsService;


//    @Test
//    public void env() {
//        saleService.approval("XSD-20220704-00001", SaleSheetState.SUCCESS);
//        UserVO user = new UserVO();
//        user.setRole(Role.ADMIN);
//        user.setName("EnjoyYourself");
//        warehouseService.approvalOutputSheet(user, "CKD-20220704-00001", WarehouseOutputSheetState.PENDING);
//        warehouseService.approvalOutputSheet(user, "CKD-20220704-00001", WarehouseOutputSheetState.SUCCESS);
//    }

    @Test
    public void warehouseServiceTest(){
        if(warehouseService==null){
            System.out.println("service也是空的");
        }else{
            System.out.println("service不是空的");
        }
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void makeSaleReturnsSheet() { // 测试销售退货单是否生成成功, 误差允许范围1%，测试结果通常在0.1%
        UserVO userVO = UserVO.builder()
                .name("xiaoshoujingli")
                .role(Role.SALE_MANAGER)
                .build();

        List<SaleSheetContentVO> saleSheetContentVOS = new ArrayList<>();
        saleSheetContentVOS.add(SaleSheetContentVO.builder()
                .pid("0000000000400000")
                .quantity(50)
                .remark("Test1-product1")
                .unitPrice(BigDecimal.valueOf(3200))
                .build());
        saleSheetContentVOS.add(SaleSheetContentVO.builder()
                .pid("0000000000400001")
                .quantity(60)
                .remark("Test1-product2")
                .unitPrice(BigDecimal.valueOf(4200))
                .build());
        SaleSheetVO saleSheetVO = SaleSheetVO.builder()
                .saleSheetContent(saleSheetContentVOS)
                .supplier(2)
                .discount(BigDecimal.valueOf(0.8))
                .voucherAmount(BigDecimal.valueOf(300))
                .remark("Test1")
                .build();

        saleService.makeSaleSheet(userVO, saleSheetVO);
        SaleSheetPO latestSaleSheet = saleSheetDao.getLatestSheet();

        String sheetId = latestSaleSheet.getId();
        saleService.approval(sheetId, SaleSheetState.PENDING_LEVEL_2);
        saleService.approval(sheetId, SaleSheetState.SUCCESS);

        List<SaleReturnsSheetContentVO> saleReturnsSheetContentVOS = new ArrayList<>();
        saleReturnsSheetContentVOS.add(SaleReturnsSheetContentVO.builder()
                .pid("0000000000400000")
                .quantity(20)
                .remark("Test1-product1")
                .unitPrice(BigDecimal.valueOf(3200))
                .build());
        saleReturnsSheetContentVOS.add(SaleReturnsSheetContentVO.builder()
                .pid("0000000000400001")
                .quantity(30)
                .remark("Test1-product2")
                .unitPrice(BigDecimal.valueOf(4200))
                .build());
        SaleReturnsSheetVO saleReturnsSheetVO = SaleReturnsSheetVO.builder()
                .saleSheetId(sheetId)
                .saleReturnsSheetContent(saleReturnsSheetContentVOS)
                .discount(BigDecimal.valueOf(0.8))
                .voucherAmount(BigDecimal.valueOf(300))
                .remark("Test1")
                .build();

        SaleReturnsSheetPO prevSheet = saleReturnsSheetDao.getLatest();
        String realSheetId = IdGenerator.generateSheetId(prevSheet == null ? null : prevSheet.getId(), "XSTHD");

        saleReturnsService.makeSaleReturnsSheet(userVO, saleReturnsSheetVO);
        SaleReturnsSheetPO latestSheet = saleReturnsSheetDao.getLatest();
        Assertions.assertNotNull(latestSheet);
        Assertions.assertEquals(realSheetId, latestSheet.getId());
        //Assertions.assertEquals(0, latestSheet.getRawTotalAmount().compareTo(BigDecimal.valueOf(412000.00)));
        BigDecimal diff = latestSheet.getFinalAmount().subtract(BigDecimal.valueOf(151861.65)).abs();
        System.out.println(latestSheet.getFinalAmount());
        System.out.println(diff);
        System.out.println(latestSheet.getFinalAmount().divide(diff, latestSheet.getFinalAmount().scale() - diff.scale()));
        Assertions.assertTrue(latestSheet.getFinalAmount().divide(diff, latestSheet.getFinalAmount().scale() - diff.scale())
                .compareTo(BigDecimal.valueOf(100)) > 0);
        Assertions.assertEquals(SaleReturnsSheetState.PENDING_LEVEL_1, latestSheet.getState());

        String returnsSheetId = latestSheet.getId();
        Assertions.assertNotNull(returnsSheetId);
        List<SaleReturnsSheetContentPO> content = saleReturnsSheetDao.findContentBySaleReturnsSheetId(returnsSheetId);
        content.sort(Comparator.comparing(SaleReturnsSheetContentPO::getPid));
        Assertions.assertEquals(2, content.size());
        Assertions.assertEquals("0000000000400000", content.get(0).getPid());
        Assertions.assertEquals(0, content.get(0).getTotalPrice().compareTo(BigDecimal.valueOf(64000.00)));
        Assertions.assertEquals("0000000000400001", content.get(1).getPid());
        Assertions.assertEquals(0, content.get(1).getTotalPrice().compareTo(BigDecimal.valueOf(126000.00)));
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void getSaleReturnsSheetByState() { // 测试按照状态获取销售单及其content是否成功
        List<SaleReturnsSheetVO> saleSheetByState = saleReturnsService.getSaleReturnsSheetByState(SaleReturnsSheetState.PENDING_LEVEL_1);
        Assertions.assertNotNull(saleSheetByState);
        Assertions.assertEquals(1, saleSheetByState.size());
        SaleReturnsSheetVO sheet1 = saleSheetByState.get(0);
        Assertions.assertNotNull(sheet1);
        Assertions.assertEquals("XSTHD-20220704-00000", sheet1.getId());

        List<SaleReturnsSheetContentVO> sheet1Content = sheet1.getSaleReturnsSheetContent();
        Assertions.assertNotNull(sheet1Content);
        Assertions.assertEquals(2, sheet1Content.size());
        sheet1Content.sort(Comparator.comparing(SaleReturnsSheetContentVO::getPid));
        Assertions.assertEquals("0000000000400000", sheet1Content.get(0).getPid());
        Assertions.assertEquals(0, sheet1Content.get(0).getTotalPrice().compareTo(BigDecimal.valueOf(64000.00)));
        Assertions.assertEquals("0000000000400001", sheet1Content.get(1).getPid());
        Assertions.assertEquals(0, sheet1Content.get(1).getTotalPrice().compareTo(BigDecimal.valueOf(126000.00)));
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void approval_exceptions_1() { // 一级审批不能直接到审批完成 (提示：可以以抛出异常的方式终止流程，这样就能触发事务回滚)
        try {
            saleReturnsService.approval("XSTHD-20220704-00000", SaleReturnsSheetState.SUCCESS);
        } catch (Exception ignore){
        } finally {
            SaleReturnsSheetPO sheet = saleReturnsSheetDao.findSheetById("XSTHD-20220704-00000");
            Assertions.assertEquals(SaleReturnsSheetState.PENDING_LEVEL_1,sheet.getState());
        }
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void approval_exceptions_2() { // 二级审批不能回到一级审批
        try {
            saleReturnsService.approval("XSTHD-20220704-00001", SaleReturnsSheetState.PENDING_LEVEL_1);
        } catch (Exception ignore){
        } finally {
            SaleReturnsSheetPO sheet = saleReturnsSheetDao.findSheetById("XSTHD-20220704-00001");
            Assertions.assertEquals(SaleReturnsSheetState.PENDING_LEVEL_2,sheet.getState());
        }
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void approval_failed() { // 测试审批失败
        saleReturnsService.approval("XSTHD-20220704-00003", SaleReturnsSheetState.FAILURE);
        SaleReturnsSheetPO sheet = saleReturnsSheetDao.findSheetById("XSTHD-20220704-00003");
        Assertions.assertEquals(SaleReturnsSheetState.FAILURE,sheet.getState());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void approval_1() { // 测试一级审批
        saleReturnsService.approval("XSTHD-20220704-00000", SaleReturnsSheetState.PENDING_LEVEL_2);
        SaleReturnsSheetPO sheet = saleReturnsSheetDao.findSheetById("XSTHD-20220704-00000");
        Assertions.assertEquals(SaleReturnsSheetState.PENDING_LEVEL_2,sheet.getState());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void approval_2() { // 测试二级审批
        saleReturnsService.approval("XSTHD-20220704-00001", SaleReturnsSheetState.SUCCESS);
        SaleReturnsSheetPO sheet = saleReturnsSheetDao.findSheetById("XSTHD-20220704-00001");
        Assertions.assertEquals(SaleReturnsSheetState.SUCCESS,sheet.getState());
    }
}