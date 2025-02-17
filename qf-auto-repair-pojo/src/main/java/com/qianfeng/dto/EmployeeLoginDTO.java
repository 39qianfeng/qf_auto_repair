package com.qianfeng.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Auther: qianfeng
 * @Date: 2025/02/12/0:28
 * @Description:
 */

@Data
@ApiModel(description = "员工登录时传递的数据模型")
public class EmployeeLoginDTO {
    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;
}
