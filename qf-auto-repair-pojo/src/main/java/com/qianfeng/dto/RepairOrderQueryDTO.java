package com.qianfeng.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Auther: qianfeng
 * @Date: 2025/02/10/18:33
 * @Description:
 */
@Data
public class RepairOrderQueryDTO {
    private Integer orderId;
    private Integer vehicleId;
    private Integer customerId;
    private LocalDateTime orderDate;
    private String status;
    private BigDecimal estimatedCost;
    private BigDecimal actualCost;
    private Integer technicianId;
    // 分页参数
    private Integer current; // 当前页码
    private Integer size; // 每页显示条数
}