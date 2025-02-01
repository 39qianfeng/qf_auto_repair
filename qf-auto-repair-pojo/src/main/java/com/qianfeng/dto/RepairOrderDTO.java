package com.qianfeng.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class RepairOrderDTO {
    private Integer orderId;
    private Integer vehicleId;
    private Integer customerId;
    private LocalDateTime orderDate;
    private String status;
    private BigDecimal estimatedCost;
    private BigDecimal actualCost;
    private Integer technicianId;
}