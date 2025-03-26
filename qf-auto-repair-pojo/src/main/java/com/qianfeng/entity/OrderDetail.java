package com.qianfeng.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("order_details")
public class OrderDetail {
    @TableId(type = IdType.AUTO)
    private Integer detailId; // 订单详情ID

    @TableField("order_id")
    private Integer orderId; // 对应的订单ID

    @TableField("part_id")
    private Integer partId; // 零件ID（可选，如果购买的是零件）

    @TableField("service_id")
    private Integer serviceId; // 服务ID（可选，如果购买的是服务）

    @TableField("item_name")
    private String itemName; // 商品名称（零件名称或服务名称）

    @TableField("item_price")
    private BigDecimal itemPrice; // 商品单价

    @TableField("quantity")
    private Integer quantity; // 购买数量

    @TableField("total_price")
    private BigDecimal totalPrice; // 该商品的总价（单价 × 数量）

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime; // 创建时间

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime; // 更新时间
}