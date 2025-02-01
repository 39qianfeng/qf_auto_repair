package com.qianfeng.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class FinanceDTO {
    private Integer transactionId;
    private Integer orderId;
    private BigDecimal amount;
    private LocalDateTime transactionDate;
    private String type;
}