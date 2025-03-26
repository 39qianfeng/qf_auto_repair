package com.qianfeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qianfeng.entity.*;
import com.qianfeng.mapper.*;
import com.qianfeng.service.WorkspaceService;
import com.qianfeng.vo.*;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: qianfeng
 * @Date: 2025/03/20/18:23
 * @Description:
 */
@Service
@Slf4j
public class WorkspaceServiceImpl implements WorkspaceService {
    @Autowired
    private RepairOrderMapper repairOrderMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private VehicleMapper vehicleMapper;
    @Autowired
    private ServiceChargeMapper serviceChargeMapper;
    @Autowired
    private PartMapper partMapper;

    /**
     * 根据时间段统计营业数据
     * @param begin
     * @param end
     * @return
     */
    @Override
    public BusinessDataVO getBusinessData(LocalDateTime begin,LocalDateTime end) {
        Map map = new HashMap();
        map.put("begin",begin);
        map.put("end",end);

        // 查询总订单数
        LambdaQueryWrapper<RepairOrder> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.gt(RepairOrder::getOrderDate,begin)
                .lt(RepairOrder::getOrderDate,end);
        Integer count = repairOrderMapper.selectCount(lambdaQueryWrapper);
        System.out.println(count);

        // 营业额
        map.put("status",RepairOrder.COMPLETED);
        BigDecimal totalAmount = repairOrderMapper.getTotalAmount(map);
        System.out.println(totalAmount);

        // 有效订单数
        lambdaQueryWrapper.eq(RepairOrder::getStatus,RepairOrder.COMPLETED);
        Integer completedCount = repairOrderMapper.selectCount(lambdaQueryWrapper);

        // 订单完成率
        Double orderCompletionRate =0.0;
        if(count != 0){
            orderCompletionRate = completedCount.doubleValue() / count;
        }

        // 平均客单价
        BigDecimal unitPrice = BigDecimal.ZERO;
        if(completedCount != 0){
            unitPrice = totalAmount.divide(BigDecimal.valueOf(completedCount));
        }

        // 新增用户数
        LambdaQueryWrapper<Customer> lambdaQueryWrapper1 = new LambdaQueryWrapper<>();
        lambdaQueryWrapper1.gt(Customer::getCreateTime,begin)
                        .lt(Customer::getCreateTime,end);

        Integer newCustomers = customerMapper.selectCount(lambdaQueryWrapper1);

        BusinessDataVO businessDataVO = BusinessDataVO.builder()
                .turnover(totalAmount)
                .validOrderCount(completedCount)
                .orderCompletionRate(orderCompletionRate)
                .unitPrice(unitPrice)
                .newUsers(newCustomers)
                .build();

        log.info("businessDataVO:"+businessDataVO);
        return businessDataVO;
    }

    /**
     * 查询订单管理数据
     * @return
     */
    @Override
    public RepairOrderOverViewVO getRepairOrderOverView() {
        Map map = new HashMap();
        map.put("begin", LocalDateTime.now().with(LocalTime.MIN));
        LambdaQueryWrapper<RepairOrder> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        // 待维修
        lambdaQueryWrapper.eq(RepairOrder::getStatus, RepairOrder.TO_BE_REPAIR);
        Integer toBeRepairCount = repairOrderMapper.selectCount(lambdaQueryWrapper);

        // 清空查询条件，准备下一次查询
        lambdaQueryWrapper.clear();

        // 维修中
        lambdaQueryWrapper.eq(RepairOrder::getStatus, RepairOrder.UNDER_REPAIR);
        Integer repairingCount = repairOrderMapper.selectCount(lambdaQueryWrapper);

        // 清空查询条件，准备下一次查询
        lambdaQueryWrapper.clear();

        // 已完成
        lambdaQueryWrapper.eq(RepairOrder::getStatus, RepairOrder.COMPLETED);
        Integer completedCount = repairOrderMapper.selectCount(lambdaQueryWrapper);

        // 清空查询条件，准备下一次查询
        lambdaQueryWrapper.clear();

        // 已取消
        lambdaQueryWrapper.eq(RepairOrder::getStatus, RepairOrder.CANCELLED);
        Integer cancelledCount = repairOrderMapper.selectCount(lambdaQueryWrapper);

        // 清空查询条件，准备下一次查询
        lambdaQueryWrapper.clear();

        // 查询所有订单
        Integer totalOrderCount = repairOrderMapper.selectCount(new LambdaQueryWrapper<>());

        // 打印结果
        System.out.println("待维修订单数量: " + toBeRepairCount);
        System.out.println("维修中订单数量: " + repairingCount);
        System.out.println("已完成订单数量: " + completedCount);
        System.out.println("已取消订单数量: " + cancelledCount);
        System.out.println("总订单数量: " + totalOrderCount);

        // 构建返回值
        RepairOrderOverViewVO repairOrderOverViewVO = RepairOrderOverViewVO.builder()
                .toBeRepairCount(toBeRepairCount)
                .repairingCount(repairingCount)
                .completedCount(completedCount)
                .cancelledCount(cancelledCount)
                .totalOrderCount(totalOrderCount)
                .build();

        return repairOrderOverViewVO;
    }

    /**
     * 查询客户数据情况
     * @return
     */
    @Override
    public CustomerOverViewVO getCustomerOverView() {
        LambdaQueryWrapper<Customer> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        // 查询客户总数
        Integer totalCustomerCount = customerMapper.selectCount(lambdaQueryWrapper);

        // 查询新增客户数
        LocalDateTime begin = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime end = LocalDateTime.now().with(LocalTime.MAX);
        lambdaQueryWrapper.gt(Customer::getCreateTime,begin)
                .lt(Customer::getCreateTime,end);
        Integer newCustomerCount = customerMapper.selectCount(lambdaQueryWrapper);

        // 创建返回值
        CustomerOverViewVO customerOverViewVO = CustomerOverViewVO.builder()
                .totalCustomerCount(totalCustomerCount)
                .newCustomerCount(newCustomerCount)
                .build();

        return customerOverViewVO;
    }

    @Override
    public GoodsOverViewVO getGoodsOverViewVO() {
        // 查询在售零件数量
        LambdaQueryWrapper<Part> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        Integer partCount = partMapper.selectCount(lambdaQueryWrapper);

        // 查询在售服务数量
        LambdaQueryWrapper<ServiceCharge> lambdaQueryWrapper1 = new LambdaQueryWrapper<>();
        Integer serviceChargeCount = serviceChargeMapper.selectCount(lambdaQueryWrapper1);

        // 构建返回值
        GoodsOverViewVO goodsOverViewVO = GoodsOverViewVO.builder()
                .partGoodsCount(partCount)
                .serviceGoodsCount(serviceChargeCount)
                .build();

        return goodsOverViewVO;
    }

    @Override
    public VehicleOverViewVO getVehicleOverViewVO() {
        // TODO
        return null;
    }

}
