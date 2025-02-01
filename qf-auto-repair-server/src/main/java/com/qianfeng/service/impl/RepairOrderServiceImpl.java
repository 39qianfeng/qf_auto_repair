package com.qianfeng.service.impl;

import com.qianfeng.dto.RepairOrderDTO;
import com.qianfeng.entity.RepairOrder;
import com.qianfeng.mapper.RepairOrderMapper;
import com.qianfeng.service.RepairOrderService;
import com.qianfeng.vo.RepairOrderVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RepairOrderServiceImpl implements RepairOrderService {

    @Autowired
    private RepairOrderMapper repairOrderMapper;

    @Override
    public List<RepairOrderVO> getAllRepairOrders() {
        List<RepairOrder> repairOrders = repairOrderMapper.selectList(null);
        return repairOrders.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    public RepairOrderVO getRepairOrderById(Integer id) {
        RepairOrder repairOrder = repairOrderMapper.selectById(id);
        return convertToVO(repairOrder);
    }

    @Override
    public RepairOrderVO createRepairOrder(RepairOrderDTO repairOrderDTO) {
        RepairOrder repairOrder = new RepairOrder();
        BeanUtils.copyProperties(repairOrderDTO, repairOrder);
        repairOrderMapper.insert(repairOrder);
        return convertToVO(repairOrder);
    }

    @Override
    public RepairOrderVO updateRepairOrder(Integer id, RepairOrderDTO repairOrderDTO) {
        RepairOrder repairOrder = repairOrderMapper.selectById(id);
        BeanUtils.copyProperties(repairOrderDTO, repairOrder);
        repairOrderMapper.updateById(repairOrder);
        return convertToVO(repairOrder);
    }

    @Override
    public void deleteRepairOrder(Integer id) {
        repairOrderMapper.deleteById(id);
    }

    private RepairOrderVO convertToVO(RepairOrder repairOrder) {
        RepairOrderVO vo = new RepairOrderVO();
        BeanUtils.copyProperties(repairOrder, vo);
        // 可以在这里添加额外的字段转换逻辑
        return vo;
    }
}