package com.qianfeng.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.math.BigDecimal;

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
}