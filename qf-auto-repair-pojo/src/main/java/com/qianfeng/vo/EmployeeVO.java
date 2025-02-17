package com.qianfeng.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class EmployeeVO {
    private Integer employeeId;
    private String name;
    private String position;
    private String phone;
    private String email;
    private String skills;
    private Integer repairOrderCount; // 额外字段：该员工处理的维修工单数量
    private String username;
}