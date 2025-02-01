package com.qianfeng.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InventoryDTO {
    private Integer inventoryId;
    private Integer partId;
    private Integer quantity;
    private LocalDateTime lastUpdated;
}