package com.qianfeng.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@TableName("parts")
@Data
public class Part {
    @TableId(type = IdType.AUTO)
    private Integer partId;

    @TableField("part_name")
    private String partName;

    @TableField("part_number")
    private String partNumber;

    @TableField("description")
    private String description;

    @TableField("stock_quantity")
    private Integer stockQuantity;

    @TableField("supplier_id")
    private Integer supplierId;

    @TableField("purchase_price")
    private BigDecimal purchasePrice;

    @TableField("sale_price")
    private BigDecimal salePrice;
    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}