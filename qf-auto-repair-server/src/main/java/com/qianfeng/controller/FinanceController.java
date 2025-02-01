package com.qianfeng.controller;

import com.qianfeng.dto.FinanceDTO;
import com.qianfeng.result.Result;
import com.qianfeng.service.FinanceService;
import com.qianfeng.vo.FinanceVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/finance")
@Api(tags = "经济数据相关接口")
public class FinanceController {

    @Autowired
    private FinanceService financeService;

    @GetMapping
    public Result<List<FinanceVO>> getAllFinanceRecords() {
        List<FinanceVO> financeRecords = financeService.getAllFinanceRecords();
        return Result.success(financeRecords);
    }

    @GetMapping("/{id}")
    public Result<FinanceVO> getFinanceRecordById(@PathVariable Integer id) {
        FinanceVO financeRecord = financeService.getFinanceRecordById(id);
        return Result.success(financeRecord);
    }

    @PostMapping
    public Result<FinanceVO> createFinanceRecord(@RequestBody FinanceDTO financeDTO) {
        FinanceVO financeRecord = financeService.createFinanceRecord(financeDTO);
        return Result.success(financeRecord);
    }

    @PutMapping("/{id}")
    public Result<FinanceVO> updateFinanceRecord(@PathVariable Integer id, @RequestBody FinanceDTO financeDTO) {
        FinanceVO financeRecord = financeService.updateFinanceRecord(id, financeDTO);
        return Result.success(financeRecord);
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteFinanceRecord(@PathVariable Integer id) {
        financeService.deleteFinanceRecord(id);
        return Result.success();
    }
}