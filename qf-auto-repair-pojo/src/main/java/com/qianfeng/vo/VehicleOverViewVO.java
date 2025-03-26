package com.qianfeng.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @Auther: qianfeng
 * @Date: 2025/03/23/23:36
 * @Description:
 */

@Data
@Builder
public class VehicleOverViewVO {
    // 待维修数量
    private Integer toBeRepairCount;
    // 维修中数量
    private Integer underRepairCount;
    // 已完成数量
    private Integer completedCount;
    // 已取消数量
    private Integer cancelledCount;
}
