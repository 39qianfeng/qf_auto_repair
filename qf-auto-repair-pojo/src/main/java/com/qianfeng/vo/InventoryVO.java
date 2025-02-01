package com.qianfeng.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InventoryVO {
    private Integer inventoryId;
    private Integer partId;
    private Integer quantity;
    private LocalDateTime lastUpdated;
    private String partName; // 额外字段：配件名称
}