package com.qianfeng.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RepairOrderOverViewVO {
    private Integer toBeRepairCount; // 待维修订单数量
    private Integer repairingCount; // 维修中订单数量
    private Integer completedCount; // 已完成订单数量
    private Integer cancelledCount; // 已取消订单数量
    private Integer totalOrderCount; // 总订单数量
}