package com.qianfeng.dto;

import lombok.Data;

/**
 * @Auther: qianfeng
 * @Date: 2025/01/29/16:14
 * @Description:
 */
@Data
public class CustomerQueryDTO {
    private Integer current; // 当前页码
    private Integer size;    // 每页大小
    private Integer customerId; // 客户id查询条件
    private String name;     // 客户名称查询条件
    private String phone;    // 客户电话查询条件
    private String email;    // 客户邮箱查询条件
    private String customerType; // 客户类型查询条件
}
