package com.qianfeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qianfeng.dto.VehicleDTO;
import com.qianfeng.dto.VehicleQueryDTO;
import com.qianfeng.entity.Customer;
import com.qianfeng.entity.RepairOrder;
import com.qianfeng.entity.Vehicle;
import com.qianfeng.mapper.CustomerMapper;
import com.qianfeng.mapper.RepairOrderMapper;
import com.qianfeng.mapper.VehicleMapper;
import com.qianfeng.service.VehicleService;
import com.qianfeng.vo.VehicleVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleMapper vehicleMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private RepairOrderMapper repairOrderMapper;

    @Override
    public List<VehicleVO> getAllVehicles() {
        List<Vehicle> vehicles = vehicleMapper.selectList(null);
        return vehicles.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    public VehicleVO getVehicleById(Integer id) {
        Vehicle vehicle = vehicleMapper.selectById(id);
        return convertToVO(vehicle);
    }

    @Override
    public VehicleVO createVehicle(VehicleDTO vehicleDTO) {
        Vehicle vehicle = new Vehicle();
        BeanUtils.copyProperties(vehicleDTO, vehicle);
        vehicleMapper.insert(vehicle);
        return convertToVO(vehicle);
    }

    @Override
    public VehicleVO updateVehicle(Integer id, VehicleDTO vehicleDTO) {
        Vehicle vehicle = vehicleMapper.selectById(id);
        BeanUtils.copyProperties(vehicleDTO, vehicle);
        vehicleMapper.updateById(vehicle);
        return convertToVO(vehicle);
    }

    @Override
    public void deleteVehicle(Integer id) {
        vehicleMapper.deleteById(id);
    }

    @Override
    public IPage<VehicleVO> pageQuery(VehicleQueryDTO vehicleQueryDTO) {
        // 使用mp创建进行分页查询
        // 创建分页对象
        int current = vehicleQueryDTO.getCurrent();
        int size = vehicleQueryDTO.getSize();
        IPage<Vehicle> page = new Page<>(current,size);
        // 创建查询条件
//        LambdaQueryWrapper<Vehicle> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        Integer vehicleId = vehicleQueryDTO.getVehicleId();
        Integer customerId = vehicleQueryDTO.getCustomerId();
        String customerType = vehicleQueryDTO.getCustomerType();
        String searchKey = vehicleQueryDTO.getSearchKey();
//        if(vehicleId != null){
//            lambdaQueryWrapper.eq(Vehicle::getVehicleId,vehicleId);
//        }
//        if(customerId != null){
//            lambdaQueryWrapper.eq(Vehicle::getCustomerId,customerId);
//        }
//        // 查询数据库
//        IPage<Vehicle> vehicleIPage = vehicleMapper.selectPage(page,lambdaQueryWrapper);
        // 查询数据库
        IPage<Vehicle> vehicleIPage = vehicleMapper.selectPageWithCustomerType(page, vehicleId, customerId, customerType,searchKey);
        // 根据查询到的数据根据车俩ID去查询车俩所属客户和车俩所相关的维修订单数
        IPage<VehicleVO> voIPage = vehicleIPage.convert(vehicle -> {
            VehicleVO vo = convertToVO(vehicle);
            // 设置所属客户姓名和客户类型
            if(vehicle.getCustomerId() != null){
                // 查询客户信息
                Customer customer = customerMapper.selectById(vehicle.getCustomerId());
                // 设置客户姓名
                vo.setCustomerName(customer.getName());
                // 设置客户类型
                vo.setCustomerType(customer.getCustomerType());
            }
            // 设置相关维修订单数
            if(vehicle.getVehicleId() != null){
                vo.setRepairOrderCount(repairOrderMapper.selectCount(
                        new LambdaQueryWrapper<RepairOrder>().eq(RepairOrder::getVehicleId, vehicle.getVehicleId())));
            }
            return vo;
        });
        // 返回分页查询结果
        return voIPage;
    }

    // 批量删除
    @Override
    public void deleteVehicleByIds(List<Integer> ids) {
        vehicleMapper.deleteBatchIds(ids);
    }

    private VehicleVO convertToVO(Vehicle vehicle) {
        VehicleVO vo = new VehicleVO();
        BeanUtils.copyProperties(vehicle, vo);
        // 可以在这里添加额外的字段转换逻辑
        return vo;
    }
}