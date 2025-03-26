package com.qianfeng.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CustomerLoginDTO {
    @ApiModelProperty("用户名")
    private String username; // 用户名
    @ApiModelProperty("密码")
    private String password; // 密码
}