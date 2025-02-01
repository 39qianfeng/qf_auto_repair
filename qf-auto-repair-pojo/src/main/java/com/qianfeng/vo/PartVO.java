package com.qianfeng.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PartVO {
    private Integer partId;
    private String partName;
    private String partNumber;
    private String description;
    private Integer stockQuantity;
    private Integer supplierId;
    private BigDecimal purchasePrice;
    private BigDecimal salePrice;
    private String supplierName; // 额外字段：供应商名称
}