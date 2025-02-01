package com.qianfeng.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentDTO {
    private Integer appointmentId;
    private Integer customerId;
    private Integer vehicleId;
    private LocalDateTime appointmentDate;
    private String status;
    private Integer technicianId;
}