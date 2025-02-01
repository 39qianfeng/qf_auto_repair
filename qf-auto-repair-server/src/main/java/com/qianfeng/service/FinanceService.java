package com.qianfeng.service;

import com.qianfeng.dto.FinanceDTO;
import com.qianfeng.vo.FinanceVO;
import java.util.List;

public interface FinanceService {
    List<FinanceVO> getAllFinanceRecords();
    FinanceVO getFinanceRecordById(Integer id);
    FinanceVO createFinanceRecord(FinanceDTO financeDTO);
    FinanceVO updateFinanceRecord(Integer id, FinanceDTO financeDTO);
    void deleteFinanceRecord(Integer id);
}