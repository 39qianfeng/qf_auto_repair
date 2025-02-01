package com.qianfeng.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class RepairOrderVO {
    private Integer orderId;
    private Integer vehicleId;
    private Integer customerId;
    private LocalDateTime orderDate;
    private String status;
    private BigDecimal estimatedCost;
    private BigDecimal actualCost;
    private Integer technicianId;
    private String vehicleLicensePlate; // 额外字段：车辆车牌号
    private String customerName; // 额外字段：客户名称
    private String technicianName; // 额外字段：技师名称
}