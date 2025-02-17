package com.qianfeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qianfeng.dto.RepairOrderDTO;
import com.qianfeng.dto.RepairOrderQueryDTO;
import com.qianfeng.entity.Customer;
import com.qianfeng.entity.RepairOrder;
import com.qianfeng.entity.Vehicle;
import com.qianfeng.mapper.CustomerMapper;
import com.qianfeng.mapper.EmployeeMapper;
import com.qianfeng.mapper.RepairOrderMapper;
import com.qianfeng.mapper.VehicleMapper;
import com.qianfeng.service.RepairOrderService;
import com.qianfeng.vo.RepairOrderVO;
import com.qianfeng.vo.VehicleVO;
import org.python.bouncycastle.cert.ocsp.Req;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RepairOrderServiceImpl implements RepairOrderService {

    @Autowired
    private RepairOrderMapper repairOrderMapper;
    @Autowired
    private VehicleMapper vehicleMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private EmployeeMapper employeeMapper;

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

    @Override
    public IPage<RepairOrderVO> pageQuery(RepairOrderQueryDTO repairOrderQueryDTO) {
        // 使用mp创建进行分页查询
        // 创建分页对象
        int current = repairOrderQueryDTO.getCurrent();
        int size = repairOrderQueryDTO.getSize();
        IPage<RepairOrder> page = new Page<>(current,size);
        // 创建查询条件
        LambdaQueryWrapper<RepairOrder> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        Integer orderId = repairOrderQueryDTO.getOrderId();
        if(orderId != null){
            lambdaQueryWrapper.eq(RepairOrder::getOrderId,orderId);
        }
        // 查询数据库
        IPage<RepairOrder> repairOrderIPage = repairOrderMapper.selectPage(page, lambdaQueryWrapper);
        // 根据查询到的数据根据车俩ID去查询车俩所属客户和车俩所相关的维修订单数
        IPage<RepairOrderVO> voIPage = repairOrderIPage.convert(repairOrder -> {
            RepairOrderVO vo = convertToVO(repairOrder);
            // 设置订单相关车俩的车牌号
            if(repairOrder.getVehicleId() != null){
                vo.setVehicleLicensePlate(vehicleMapper.selectById(repairOrder.getVehicleId()).getLicensePlate());
            }
            // 设置订单相关顾客名
            if(repairOrder.getCustomerId() != null){
                vo.setCustomerName(customerMapper.selectById(repairOrder.getCustomerId()).getName());
            }
            // 设置订单相关技师名
            if(repairOrder.getTechnicianId() != null){
                vo.setTechnicianName(employeeMapper.selectById(repairOrder.getTechnicianId()).getName());
            }
            return vo;
        });
        // 返回分页查询结果
        return voIPage;
    }

    private RepairOrderVO convertToVO(RepairOrder repairOrder) {
        RepairOrderVO vo = new RepairOrderVO();
        BeanUtils.copyProperties(repairOrder, vo);
        // 可以在这里添加额外的字段转换逻辑
        return vo;
    }
}