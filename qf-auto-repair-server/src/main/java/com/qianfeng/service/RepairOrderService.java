package com.qianfeng.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qianfeng.dto.RepairOrderDTO;
import com.qianfeng.dto.RepairOrderQueryDTO;
import com.qianfeng.vo.RepairOrderVO;
import java.util.List;

public interface RepairOrderService {
    List<RepairOrderVO> getAllRepairOrders();
    RepairOrderVO getRepairOrderById(Integer id);
    RepairOrderVO createRepairOrder(RepairOrderDTO repairOrderDTO);
    RepairOrderVO updateRepairOrder(Integer id, RepairOrderDTO repairOrderDTO);
    void deleteRepairOrder(Integer id);

    IPage<RepairOrderVO> pageQuery(RepairOrderQueryDTO repairOrderQueryDTO);
}