package com.qianfeng.vo;

import com.qianfeng.dto.CartItemDTO;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemVO {
    private Integer cartId; // 购物车项ID
    private Integer customerId; // 客户ID
    private Integer itemId; // 商品ID
    private String itemType; // 商品类型（PART 或 SERVICE）
    private String itemName; // 商品名称
    private BigDecimal itemPrice; // 商品单价
    private Integer number; // 商品数量
    private BigDecimal totalPrice; // 商品总价（单价 × 数量）
}