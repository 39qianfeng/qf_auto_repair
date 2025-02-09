package com.qianfeng.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@TableName("employees")
@Data
public class Employee {
    @TableId(type = IdType.AUTO)
    private Integer employeeId;

    @TableField("name")
    private String name;

    @TableField("position")
    private String position;

    @TableField("phone")
    private String phone;

    @TableField("email")
    private String email;

    @TableField("skills")
    private String skills;
    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}