package com.qianfeng.dto;

import lombok.Data;

@Data
public class SupplierDTO {
    private Integer supplierId;
    private String name;
    private String contactPerson;
    private String phone;
    private String email;
    private String address;
}