package com.qianfeng.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@TableName("inventory")
@Data
public class Inventory {
    @TableId(type = IdType.AUTO)
    private Integer inventoryId;

    @TableField("part_id")
    private Integer partId;

    @TableField("quantity")
    private Integer quantity;

    @TableField("last_updated")
    private LocalDateTime lastUpdated;
}