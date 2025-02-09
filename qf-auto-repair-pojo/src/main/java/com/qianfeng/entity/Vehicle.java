package com.qianfeng.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.Api;
import lombok.Data;

import java.time.LocalDateTime;

@TableName("vehicles")
@Data
@Api(tags = "车俩信息相关接口")
public class Vehicle {
    @TableId(type = IdType.AUTO)
    private Integer vehicleId;

    @TableField("customer_id")
    private Integer customerId;

    @TableField("license_plate")
    private String licensePlate;

    @TableField("brand")
    private String brand;

    @TableField("model")
    private String model;

    @TableField("vin")
    private String vin;

    @TableField("engine_no")
    private String engineNo;

    @TableField("mileage")
    private Integer mileage;
}