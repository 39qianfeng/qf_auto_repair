package com.qianfeng.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 收费服务类
 */
@Data
@TableName("service_charges")
public class ServiceCharge {
    /**
     * 服务ID（主键）
     */
    @TableId(type = IdType.AUTO)
    private Integer serviceId;

    /**
     * 服务名称
     */
    @TableField("service_name")
    private String serviceName;

    /**
     * 服务描述
     */
    @TableField("description")
    private String description;

    /**
     * 服务价格
     */
    @TableField("price")
    private BigDecimal price;

    /**
     * 服务时长（单位：小时）
     */
    @TableField("duration")
    private BigDecimal duration;

    /**
     * 是否需要预约（0：不需要，1：需要）
     */
    @TableField("is_appointment_required")
    private Integer isAppointmentRequired;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}