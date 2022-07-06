package com.nju.edu.erp.service;

import com.nju.edu.erp.enums.BaseEnum;
import com.nju.edu.erp.model.po.SheetPO;
import com.nju.edu.erp.model.vo.SheetVO;
import com.nju.edu.erp.model.vo.UserVO;

import java.util.List;

public interface SheetService<Sheetpo extends SheetPO, Sheetvo extends SheetVO, SheetState extends BaseEnum<?, String>> {
    /**
     * 制定单
     * @param userVO 用户VO
     * @param sheetVO 单VO
     */
    void makeSheet(UserVO userVO, Sheetvo sheetVO);

    /**
     * 根据状态获取单(state == null 则获取所有单)
     * @param state 单状态
     * @return 单
     */
    List<Sheetvo> getSheetByState(SheetState state);

    /**
     * 根据单id进行审批(state), 注意，有的有两层审批，有的只要总经理审批
     * 在controller层进行权限控制
     * @param sheetId 单id
     * @param state 单修改后的状态
     */
    void approval(String sheetId, SheetState state);

    /**
     * 根据单Id搜索进货单信息
     * @param sheetId 单Id
     * @return 单全部信息
     */
    Sheetvo getSheetById(String sheetId);
}
