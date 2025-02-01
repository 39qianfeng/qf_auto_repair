package com.qianfeng.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.Api;
import lombok.Data;

@TableName("suppliers")
@Data
@Api(tags = "供应商相关接口")
public class Supplier {
    @TableId(type = IdType.AUTO)
    private Integer supplierId;

    @TableField("name")
    private String name;

    @TableField("contact_person")
    private String contactPerson;

    @TableField("phone")
    private String phone;

    @TableField("email")
    private String email;

    @TableField("address")
    private String address;
}