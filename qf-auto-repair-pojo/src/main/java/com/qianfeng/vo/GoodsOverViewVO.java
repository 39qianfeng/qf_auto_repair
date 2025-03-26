package com.qianfeng.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @Auther: qianfeng
 * @Date: 2025/03/23/14:15
 * @Description:
 */

@Data
@Builder
public class GoodsOverViewVO {
    // 零件商品数量
    private Integer partGoodsCount;
    // 服务商品数量
    private Integer serviceGoodsCount;
}
