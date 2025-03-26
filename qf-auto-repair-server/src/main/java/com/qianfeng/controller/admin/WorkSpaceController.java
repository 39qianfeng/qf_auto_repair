package com.qianfeng.controller.admin;

import com.qianfeng.result.Result;
import com.qianfeng.service.WorkspaceService;
import com.qianfeng.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @Auther: qianfeng
 * @Date: 2025/03/19/19:09
 * @Description:
 */
@RestController
@Slf4j
@RequestMapping("/api/workSpace")
@Api(tags = "工作台相关接口")
public class WorkSpaceController {

    @Autowired
    private WorkspaceService workspaceService;

    /**
     * 工作台今日业务数据查询
     * @return
     */
    @GetMapping("/businessData")
    @ApiOperation("工作台今日业务数据查询")
    public Result<BusinessDataVO> businessData(){
        // 当天开始时间
        LocalDateTime begin = LocalDateTime.now().with(LocalTime.MIN);
        // 当天结束时间
        LocalDateTime end = LocalDateTime.now().with(LocalTime.MAX);
        // 根据时间段统计营业数据
        BusinessDataVO businessDataVO = workspaceService.getBusinessData(begin,end);
        return Result.success(businessDataVO);
    }

    /**
     * 查询维修工单总览
     * @return
     */
    @GetMapping("/overviewRepairOrders")
    @ApiOperation("查询维修工单总览")
    public Result<RepairOrderOverViewVO> repairOrderOverView(){
        RepairOrderOverViewVO repairOrderOverViewVO = workspaceService.getRepairOrderOverView();
        return Result.success(repairOrderOverViewVO);
    }

    /**
     * 查询客户总览
     * @return
     */
    @GetMapping("/overviewCustomers")
    @ApiOperation("查询客户总览")
    public Result<CustomerOverViewVO> customerOverView(){
        CustomerOverViewVO customerOverViewVO = workspaceService.getCustomerOverView();
        return Result.success(customerOverViewVO);
    }

    @GetMapping("/overviewGoods")
    @ApiOperation("查询商品总览")
    public Result<GoodsOverViewVO> goodsOverView(){
        GoodsOverViewVO goodsOverViewVO = workspaceService.getGoodsOverViewVO();
        return Result.success(goodsOverViewVO);
    }
}