package com.qianfeng.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@TableName("finance")
@Data
public class Finance {
    @TableId(type = IdType.AUTO)
    private Integer transactionId;

    @TableField("order_id")
    private Integer orderId;

    @TableField("amount")
    private BigDecimal amount;

    @TableField("transaction_date")
    private LocalDateTime transactionDate;

    @TableField("type")
    private String type;
}