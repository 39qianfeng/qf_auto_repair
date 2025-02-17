package com.qianfeng.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@TableName("repair_orders")
@Data
public class RepairOrder {
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

    @TableField("estimated_cost")
    private BigDecimal estimatedCost;

    @TableField("actual_cost")
    private BigDecimal actualCost;

    @TableField("technician_id")
    private Integer technicianId; // 对应着employee表中的employeeId
}