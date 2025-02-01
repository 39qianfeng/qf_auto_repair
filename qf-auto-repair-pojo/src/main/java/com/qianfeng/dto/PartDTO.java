package com.qianfeng.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PartDTO {
    private Integer partId;
    private String partName;
    private String partNumber;
    private String description;
    private Integer stockQuantity;
    private Integer supplierId;
    private BigDecimal purchasePrice;
    private BigDecimal salePrice;
}