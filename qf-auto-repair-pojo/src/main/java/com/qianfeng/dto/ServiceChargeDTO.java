package com.qianfeng.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Auther: qianfeng
 * @Date: 2025/02/25/22:44
 * @Description:
 */
@Data
public class ServiceChargeDTO {
    private Integer serviceId; // 可选字段，用于更新时指定 ID
    private String serviceName;
    private String description;
    private BigDecimal price;
    private BigDecimal duration;
    private Integer isAppointmentRequired;
}