package com.qianfeng.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentVO {
    private Integer appointmentId;
    private Integer customerId;
    private Integer vehicleId;
    private LocalDateTime appointmentDate;
    private String status;
    private Integer technicianId;
    private String vehicleLicensePlate; // 额外字段：车辆车牌号
    private String customerName; // 额外字段：客户名称
    private String technicianName; // 额外字段：技师名称
}