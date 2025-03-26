package com.qianfeng.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Auther: qianfeng
 * @Date: 2025/02/26/17:20
 * @Description:
 */

@Data
public class CartItemDTO {
    private Integer cartId;

    private Integer customerId;

    private Integer itemId;

    private String itemType;  // 区分商品类型

    private String itemName;

    private BigDecimal itemPrice;

    private Integer number;
}