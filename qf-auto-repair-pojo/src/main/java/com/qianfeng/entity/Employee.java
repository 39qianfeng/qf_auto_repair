package com.qianfeng.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

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
}