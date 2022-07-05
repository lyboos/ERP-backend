package com.nju.edu.erp.dao;

import com.nju.edu.erp.enums.BaseEnum;
import com.nju.edu.erp.model.po.SheetPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SheetDao<Sheetpo extends SheetPO, State extends BaseEnum<?, String>> {
    /**
     * 获取最近一条单
     * @return 最近一条单
     */
    Sheetpo getLatest();

    /**
     * 存入一条进货单记录
     * @param toSave 一条进货单记录
     * @return 影响的行数
     */
    int save(Sheetpo toSave);

    /**
     * 返回所有单
     * @return 单列表
     */
    List<Sheetpo> findAll();

    /**
     * 根据state返回单
     * @param state 单状态
     * @return 单列表
     */
    List<Sheetpo> findAllByState(State state);

    int updateState(String sheetId, State state);

    int updateStateV2(String sheetId, State prevState, State state);

    Sheetpo findSheetById(String sheetId);
}
