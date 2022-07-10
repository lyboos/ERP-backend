package com.nju.edu.erp.model.vo;

import java.util.List;

public interface SheetWithContentVO<E extends SheetContentVO> extends SheetVO {
    E getSheetContent();
}
