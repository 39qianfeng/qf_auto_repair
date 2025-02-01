package com.qianfeng.controller;

import com.qianfeng.dto.InventoryDTO;
import com.qianfeng.result.Result;
import com.qianfeng.service.InventoryService;
import com.qianfeng.vo.InventoryVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@Api(tags = "库存相关接口")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    public Result<List<InventoryVO>> getAllInventory() {
        List<InventoryVO> inventoryList = inventoryService.getAllInventory();
        return Result.success(inventoryList);
    }

    @GetMapping("/{id}")
    public Result<InventoryVO> getInventoryById(@PathVariable Integer id) {
        InventoryVO inventory = inventoryService.getInventoryById(id);
        return Result.success(inventory);
    }

    @PostMapping
    public Result<InventoryVO> createInventory(@RequestBody InventoryDTO inventoryDTO) {
        InventoryVO inventory = inventoryService.createInventory(inventoryDTO);
        return Result.success(inventory);
    }

    @PutMapping("/{id}")
    public Result<InventoryVO> updateInventory(@PathVariable Integer id, @RequestBody InventoryDTO inventoryDTO) {
        InventoryVO inventory = inventoryService.updateInventory(id, inventoryDTO);
        return Result.success(inventory);
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteInventory(@PathVariable Integer id) {
        inventoryService.deleteInventory(id);
        return Result.success();
    }
}