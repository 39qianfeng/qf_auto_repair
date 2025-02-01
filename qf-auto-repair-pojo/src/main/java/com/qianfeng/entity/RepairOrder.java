package com.qianfeng.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
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

    @TableField("order_date")
    private LocalDateTime orderDate;

    @TableField("status")
    private String status;

    @TableField("estimated_cost")
    private BigDecimal estimatedCost;

    @TableField("actual_cost")
    private BigDecimal actualCost;

    @TableField("technician_id")
    private Integer technicianId;
}