package com.qianfeng.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerLoginVO {
    @ApiModelProperty("主键值")
    private Integer customerId;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("jwt令牌")
    private String token;
    @ApiModelProperty("用户类型")
    private String type;
}