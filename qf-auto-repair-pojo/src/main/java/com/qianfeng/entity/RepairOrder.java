package com.qianfeng.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@TableName("repair_orders")
@Data
public class RepairOrder {
    public static final String TO_BE_REPAIR = "待维修";
    public static final String UNDER_REPAIR = "维修中";
    public static final String COMPLETED = "已完成";
    public static final String CANCELLED = "已取消";
    @TableId(type = IdType.AUTO)
    private Integer orderId;

    @TableField("vehicle_id")
    private Integer vehicleId;

    @TableField("customer_id")
    private Integer customerId;

    @TableField(value = "order_date",fill = FieldFill.INSERT)
    private LocalDateTime orderDate;

    @TableField("status")
    private String status;

    //orderCost 属性
    @TableField("order_cost")
    private BigDecimal orderCost;

    @TableField("technician_id")
    private Integer technicianId; // 对应着employee表中的employeeId
}