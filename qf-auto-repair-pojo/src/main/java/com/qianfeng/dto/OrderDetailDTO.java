package com.qianfeng.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // 避免传输空值
public class OrderDetailDTO {
    private Integer detailId; // 订单详情ID
    private Integer orderId; // 订单ID
    private Integer partId; // 零件ID（可选）
    private Integer serviceId; // 服务ID（可选）
    private String itemName; // 商品名称（零件名称或服务名称）
    private BigDecimal itemPrice; // 商品单价
    private Integer quantity; // 购买数量
    private BigDecimal totalPrice; // 商品总价（单价 × 数量）
}