package com.qianfeng.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
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
}