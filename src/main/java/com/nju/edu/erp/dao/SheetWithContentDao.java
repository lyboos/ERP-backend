package com.nju.edu.erp.dao;

import com.nju.edu.erp.enums.BaseEnum;
import com.nju.edu.erp.model.po.SheetContentPO;
import com.nju.edu.erp.model.po.SheetPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SheetWithContentDao<Sheetpo extends SheetPO, SheetConentpo extends SheetContentPO, State extends BaseEnum<?, String>>
        extends SheetDao<Sheetpo, State> {

    void saveBatch(List<SheetConentpo> sheetContent);

    List<SheetConentpo> findContentBySheetId(String sheetId);
}
