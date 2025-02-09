package com.qianfeng.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qianfeng.dto.VehicleDTO;
import com.qianfeng.dto.VehicleQueryDTO;
import com.qianfeng.vo.VehicleVO;
import java.util.List;

public interface VehicleService {
    List<VehicleVO> getAllVehicles();
    VehicleVO getVehicleById(Integer id);
    VehicleVO createVehicle(VehicleDTO vehicleDTO);
    VehicleVO updateVehicle(Integer id, VehicleDTO vehicleDTO);
    void deleteVehicle(Integer id);

    IPage<VehicleVO> pageQuery(VehicleQueryDTO vehicleQueryDTO);

    void deleteVehicleByIds(List<Integer> ids);
}