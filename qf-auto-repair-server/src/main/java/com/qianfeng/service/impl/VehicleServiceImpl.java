package com.qianfeng.service.impl;

import com.qianfeng.dto.VehicleDTO;
import com.qianfeng.entity.Vehicle;
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

    private VehicleVO convertToVO(Vehicle vehicle) {
        VehicleVO vo = new VehicleVO();
        BeanUtils.copyProperties(vehicle, vo);
        // 可以在这里添加额外的字段转换逻辑
        return vo;
    }
}