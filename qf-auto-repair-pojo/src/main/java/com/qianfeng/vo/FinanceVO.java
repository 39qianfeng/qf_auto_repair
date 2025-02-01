package com.qianfeng.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class FinanceVO {
    private Integer transactionId;
    private Integer orderId;
    private BigDecimal amount;
    private LocalDateTime transactionDate;
    private String type;
    private String orderStatus; // 额外字段：关联的维修工单状态
}