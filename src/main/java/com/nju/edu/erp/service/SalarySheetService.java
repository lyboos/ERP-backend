package com.nju.edu.erp.service;

import com.nju.edu.erp.enums.sheetState.SalarySheetState;
import com.nju.edu.erp.model.po.SalarySheetPO;
import com.nju.edu.erp.model.vo.SalarySheetVO;
import org.springframework.stereotype.Service;

@Service
public interface SalarySheetService extends SheetService<SalarySheetPO, SalarySheetVO, SalarySheetState> {
}
