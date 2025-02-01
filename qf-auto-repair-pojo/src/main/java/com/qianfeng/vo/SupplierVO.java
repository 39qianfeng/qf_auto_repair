package com.qianfeng.vo;

import lombok.Data;

@Data
public class SupplierVO {
    private Integer supplierId;
    private String name;
    private String contactPerson;
    private String phone;
    private String email;
    private String address;
    private Integer partCount; // 额外字段：该供应商提供的配件数量
}