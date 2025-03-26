package com.qianfeng.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PartQueryDTO {
    private Integer partId;
    private String partName;
    private String partNumber;
    private String description;
    private Integer stockQuantity;
    private Integer supplierId;
    private BigDecimal purchasePrice;
    private BigDecimal salePrice;
    private Integer current;            // 当前页码
    private Integer size;            // 每页显示条数
}