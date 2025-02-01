package com.qianfeng.controller;

import com.qianfeng.dto.VehicleDTO;
import com.qianfeng.result.Result;
import com.qianfeng.service.VehicleService;
import com.qianfeng.vo.VehicleVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@Api(tags = "车俩信息相关接口")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping
    public Result<List<VehicleVO>> getAllVehicles() {
        List<VehicleVO> vehicles = vehicleService.getAllVehicles();
        return Result.success(vehicles);
    }

    @GetMapping("/{id}")
    public Result<VehicleVO> getVehicleById(@PathVariable Integer id) {
        VehicleVO vehicle = vehicleService.getVehicleById(id);
        return Result.success(vehicle);
    }

    @PostMapping
    public Result<VehicleVO> createVehicle(@RequestBody VehicleDTO vehicleDTO) {
        VehicleVO vehicle = vehicleService.createVehicle(vehicleDTO);
        return Result.success(vehicle);
    }

    @PutMapping("/{id}")
    public Result<VehicleVO> updateVehicle(@PathVariable Integer id, @RequestBody VehicleDTO vehicleDTO) {
        VehicleVO vehicle = vehicleService.updateVehicle(id, vehicleDTO);
        return Result.success(vehicle);
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteVehicle(@PathVariable Integer id) {
        vehicleService.deleteVehicle(id);
        return Result.success();
    }
}