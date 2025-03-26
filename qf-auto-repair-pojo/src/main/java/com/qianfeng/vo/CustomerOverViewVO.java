package com.qianfeng.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerOverViewVO {
    private Integer totalCustomerCount; // 客户总数
    private Integer newCustomerCount; // 新增客户数量
    // 其他客户总览数据字段
}