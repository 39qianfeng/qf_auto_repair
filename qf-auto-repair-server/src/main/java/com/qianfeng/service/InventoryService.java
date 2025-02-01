package com.qianfeng.service;

import com.qianfeng.dto.InventoryDTO;
import com.qianfeng.vo.InventoryVO;
import java.util.List;

public interface InventoryService {
    List<InventoryVO> getAllInventory();
    InventoryVO getInventoryById(Integer id);
    InventoryVO createInventory(InventoryDTO inventoryDTO);
    InventoryVO updateInventory(Integer id, InventoryDTO inventoryDTO);
    void deleteInventory(Integer id);
}