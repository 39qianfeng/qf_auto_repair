package com.qianfeng.vo;

import lombok.Data;

@Data
public class CustomerVO {
    private Integer customerId;
    private String name;
    private String phone;
    private String email;
    private String address;
    private String customerType;
    private Integer vehicleCount; // 额外字段：该客户拥有的车辆数量
    private Integer repairOrderCount; // 额外字段：该客户的维修工单数量
}