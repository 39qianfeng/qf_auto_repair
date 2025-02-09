package com.qianfeng.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@TableName("appointments")
@Data
public class Appointment {
    @TableId(type = IdType.AUTO)
    private Integer appointmentId;

    @TableField("customer_id")
    private Integer customerId;

    @TableField("vehicle_id")
    private Integer vehicleId;

    @TableField("appointment_date")
    private LocalDateTime appointmentDate;

    @TableField("status")
    private String status;

    @TableField("technician_id")
    private Integer technicianId;
    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}