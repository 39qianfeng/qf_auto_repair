package com.qianfeng.controller;

import com.qianfeng.dto.RepairOrderDTO;
import com.qianfeng.result.Result;
import com.qianfeng.service.RepairOrderService;
import com.qianfeng.vo.RepairOrderVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/repair-orders")
@Api(tags = "维修订单相关接口")
public class RepairOrderController {

    @Autowired
    private RepairOrderService repairOrderService;

    @GetMapping
    public Result<List<RepairOrderVO>> getAllRepairOrders() {
        List<RepairOrderVO> repairOrders = repairOrderService.getAllRepairOrders();
        return Result.success(repairOrders);
    }

    @GetMapping("/{id}")
    public Result<RepairOrderVO> getRepairOrderById(@PathVariable Integer id) {
        RepairOrderVO repairOrder = repairOrderService.getRepairOrderById(id);
        return Result.success(repairOrder);
    }

    @PostMapping
    public Result<RepairOrderVO> createRepairOrder(@RequestBody RepairOrderDTO repairOrderDTO) {
        RepairOrderVO repairOrder = repairOrderService.createRepairOrder(repairOrderDTO);
        return Result.success(repairOrder);
    }

    @PutMapping("/{id}")
    public Result<RepairOrderVO> updateRepairOrder(@PathVariable Integer id, @RequestBody RepairOrderDTO repairOrderDTO) {
        RepairOrderVO repairOrder = repairOrderService.updateRepairOrder(id, repairOrderDTO);
        return Result.success(repairOrder);
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteRepairOrder(@PathVariable Integer id) {
        repairOrderService.deleteRepairOrder(id);
        return Result.success();
    }
}