package com.qianfeng.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@TableName("customers")
@Data
public class Customer {
    @TableId(type = IdType.AUTO)
    private Integer customerId;

    @TableField("name")
    private String name;

    @TableField("phone")
    private String phone;

    @TableField("email")
    private String email;

    @TableField("address")
    private String address;

    @TableField("customer_type")
    private String customerType;
}