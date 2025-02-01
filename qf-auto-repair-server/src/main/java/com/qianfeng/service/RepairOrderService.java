package com.qianfeng.service;

import com.qianfeng.dto.RepairOrderDTO;
import com.qianfeng.vo.RepairOrderVO;
import java.util.List;

public interface RepairOrderService {
    List<RepairOrderVO> getAllRepairOrders();
    RepairOrderVO getRepairOrderById(Integer id);
    RepairOrderVO createRepairOrder(RepairOrderDTO repairOrderDTO);
    RepairOrderVO updateRepairOrder(Integer id, RepairOrderDTO repairOrderDTO);
    void deleteRepairOrder(Integer id);
}