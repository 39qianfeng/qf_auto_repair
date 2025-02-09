package com.qianfeng.vo;

import lombok.Data;

@Data
public class VehicleVO {
    private Integer vehicleId;
    private Integer customerId;
    private String licensePlate;
    private String brand;
    private String model;
    private String vin;
    private String engineNo;
    private Integer mileage;
    private String customerName; // 额外字段：车辆所属客户的名称
    private String customerType; // 额外字段，该车俩所属客户的类型
    private Integer repairOrderCount; // 额外字段：该车辆的维修工单数量
}