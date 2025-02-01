package com.qianfeng.dto;

import lombok.Data;

@Data
public class VehicleDTO {
    private Integer vehicleId;
    private Integer customerId;
    private String licensePlate;
    private String brand;
    private String model;
    private String vin;
    private String engineNo;
    private Integer mileage;
}