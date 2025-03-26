package com.qianfeng.service;

import com.qianfeng.vo.*;

import java.time.LocalDateTime;

/**
 * @Auther: qianfeng
 * @Date: 2025/03/19/19:50
 * @Description:
 */
public interface WorkspaceService {
    BusinessDataVO getBusinessData(LocalDateTime begin,LocalDateTime end);

    RepairOrderOverViewVO getRepairOrderOverView();

    CustomerOverViewVO getCustomerOverView();

    GoodsOverViewVO getGoodsOverViewVO();

    VehicleOverViewVO getVehicleOverViewVO();
}
