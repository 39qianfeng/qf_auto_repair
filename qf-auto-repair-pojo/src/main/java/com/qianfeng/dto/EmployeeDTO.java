package com.qianfeng.dto;

import lombok.Data;

@Data
public class EmployeeDTO {
    private Integer employeeId;
    private String name;
    private String position;
    private String phone;
    private String email;
    private String skills;
}