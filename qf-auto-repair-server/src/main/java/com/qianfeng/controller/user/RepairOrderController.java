package com.qianfeng.controller.user;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qianfeng.dto.RepairOrderDTO;
import com.qianfeng.dto.RepairOrderQueryDTO;
import com.qianfeng.entity.CartItem;
import com.qianfeng.result.Result;
import com.qianfeng.service.CartService;
import com.qianfeng.service.RepairOrderService;
import com.qianfeng.vo.RepairOrderVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/customer/repair-orders")
@Api(tags = "顾客维修订单相关接口")
@Component("customerRepairOrderController")
public class RepairOrderController {

    @Autowired
    private RepairOrderService repairOrderService;
    @Autowired
    private CartService cartService;

    // 创建订单
    @PostMapping("/createOrder")
    public Result<Void> createOrder(@RequestParam Integer customerId) {
        RepairOrderDTO repairOrderDTO = new RepairOrderDTO();
        repairOrderDTO.setCustomerId(customerId);

        // 创建订单
        repairOrderService.createRepairOrder(repairOrderDTO);

        return Result.success();
    }

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
        System.out.println(id);
        RepairOrderVO repairOrder = repairOrderService.updateRepairOrder(id, repairOrderDTO);
        return Result.success(repairOrder);
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteRepairOrder(@PathVariable Integer id) {
        repairOrderService.deleteRepairOrder(id);
        return Result.success();
    }

    /**
     * 批量查询
     */
    @GetMapping("/page")
    public Result<IPage<RepairOrderVO>> page(RepairOrderQueryDTO repairOrderQueryDTO){
        IPage<RepairOrderVO> iPage = repairOrderService.pageQuery(repairOrderQueryDTO);
        return Result.success(iPage);
    }
}