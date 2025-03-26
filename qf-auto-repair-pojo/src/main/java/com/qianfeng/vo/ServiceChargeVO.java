package com.qianfeng.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Auther: qianfeng
 * @Date: 2025/02/25/22:44
 * @Description:
 */
@Data
public class ServiceChargeVO {
    private Integer serviceId;
    private String serviceName;
    private String description;
    private BigDecimal price;
    private BigDecimal duration;
    private Integer isAppointmentRequired;
    private LocalDateTime createTime; // 额外字段：创建时间
    private LocalDateTime updateTime; // 额外字段：更新时间
}