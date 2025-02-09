package com.qianfeng.dto;

import lombok.Data;

/**
 * @Auther: qianfeng
 * @Date: 2025/02/03/1:24
 * @Description:
 */
@Data
public class VehicleQueryDTO {
    private Integer vehicleId;
    private Integer customerId;
    private String licensePlate;
    private String brand;
    private String model;
    private String vin;
    private String engineNo;
    private Integer mileage;
    private String customerType;
    private String searchKey;
    // 分页参数
    private Integer current;            // 当前页码
    private Integer size;            // 每页显示条数
}
