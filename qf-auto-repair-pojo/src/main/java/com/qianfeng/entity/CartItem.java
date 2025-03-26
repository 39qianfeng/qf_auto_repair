package com.qianfeng.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Auther: qianfeng
 * @Date: 2025/02/26/17:20
 * @Description:
 */

@Data
@TableName("Cart")
public class CartItem {
    @TableId(type = IdType.AUTO)
    private Integer cartId;

    private Integer customerId;

    private Integer itemId;

    private String itemType;  // 区分商品类型

    private String itemName;

    private BigDecimal itemPrice;

    private Integer number;

    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}