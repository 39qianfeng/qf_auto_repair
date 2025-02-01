package com.qianfeng.service.impl;

import com.qianfeng.dto.InventoryDTO;
import com.qianfeng.entity.Inventory;
import com.qianfeng.mapper.InventoryMapper;
import com.qianfeng.service.InventoryService;
import com.qianfeng.vo.InventoryVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryMapper inventoryMapper;

    @Override
    public List<InventoryVO> getAllInventory() {
        List<Inventory> inventories = inventoryMapper.selectList(null);
        return inventories.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    public InventoryVO getInventoryById(Integer id) {
        Inventory inventory = inventoryMapper.selectById(id);
        return convertToVO(inventory);
    }

    @Override
    public InventoryVO createInventory(InventoryDTO inventoryDTO) {
        Inventory inventory = new Inventory();
        BeanUtils.copyProperties(inventoryDTO, inventory);
        inventoryMapper.insert(inventory);
        return convertToVO(inventory);
    }

    @Override
    public InventoryVO updateInventory(Integer id, InventoryDTO inventoryDTO) {
        Inventory inventory = inventoryMapper.selectById(id);
        BeanUtils.copyProperties(inventoryDTO, inventory);
        inventoryMapper.updateById(inventory);
        return convertToVO(inventory);
    }

    @Override
    public void deleteInventory(Integer id) {
        inventoryMapper.deleteById(id);
    }

    private InventoryVO convertToVO(Inventory inventory) {
        InventoryVO vo = new InventoryVO();
        BeanUtils.copyProperties(inventory, vo);
        // 可以在这里添加额外的字段转换逻辑
        return vo;
    }
}