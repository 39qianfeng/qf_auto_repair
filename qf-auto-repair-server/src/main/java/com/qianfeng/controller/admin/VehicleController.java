package com.qianfeng.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qianfeng.dto.VehicleDTO;
import com.qianfeng.dto.VehicleQueryDTO;
import com.qianfeng.result.Result;
import com.qianfeng.service.VehicleService;
import com.qianfeng.vo.VehicleVO;
import io.swagger.annotations.Api;
import jnr.ffi.annotations.In;
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
    // 删除
    @DeleteMapping("/{id}")
    public Result<Void> deleteVehicle(@PathVariable Integer id) {
        vehicleService.deleteVehicle(id);
        return Result.success();
    }
    // 批量删除
    @DeleteMapping("/deleteByIds")
    public Result<Void> deleteVehicleByIds(@RequestParam List<Integer> ids){
        vehicleService.deleteVehicleByIds(ids);
        return Result.success();
    }
    @GetMapping("/page")
    public Result<IPage<VehicleVO>> page(VehicleQueryDTO vehicleQueryDTO) {
        System.out.println(vehicleQueryDTO);
        IPage<VehicleVO> page = vehicleService.pageQuery(vehicleQueryDTO);
        return Result.success(page);
    }
}