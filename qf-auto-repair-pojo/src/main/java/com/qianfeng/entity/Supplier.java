package com.qianfeng.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.Api;
import lombok.Data;

import java.time.LocalDateTime;

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
    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}