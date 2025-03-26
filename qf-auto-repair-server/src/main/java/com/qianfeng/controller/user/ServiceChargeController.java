package com.qianfeng.controller.user;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qianfeng.dto.ServiceChargeDTO;
import com.qianfeng.dto.ServiceChargeQueryDTO;
import com.qianfeng.result.Result;
import com.qianfeng.service.ServiceChargeService;
import com.qianfeng.vo.ServiceChargeVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer/service-charges")
@Api(tags = "顾客服务收费相关接口")
@Component("customerServiceChargeController")
public class ServiceChargeController {

    @Autowired
    private ServiceChargeService serviceChargeService;

    @GetMapping
    public Result<List<ServiceChargeVO>> getAllServiceCharges() {
        List<ServiceChargeVO> serviceCharges = serviceChargeService.getAllServiceCharges();
        return Result.success(serviceCharges);
    }

    @GetMapping("/{id}")
    public Result<ServiceChargeVO> getServiceChargeById(@PathVariable Integer id) {
        ServiceChargeVO serviceCharge = serviceChargeService.getServiceChargeById(id);
        return Result.success(serviceCharge);
    }

    @PostMapping
    public Result<ServiceChargeVO> createServiceCharge(@RequestBody ServiceChargeDTO serviceChargeDTO) {
        ServiceChargeVO serviceCharge = serviceChargeService.createServiceCharge(serviceChargeDTO);
        return Result.success(serviceCharge);
    }

    @PutMapping("/{id}")
    public Result<ServiceChargeVO> updateServiceCharge(@PathVariable Integer id, @RequestBody ServiceChargeDTO serviceChargeDTO) {
        ServiceChargeVO serviceCharge = serviceChargeService.updateServiceCharge(id, serviceChargeDTO);
        return Result.success(serviceCharge);
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteServiceCharge(@PathVariable Integer id) {
        serviceChargeService.deleteServiceCharge(id);
        return Result.success();
    }

    /**
     * 分页查询
     */
    @GetMapping("/page")
    public Result<IPage<ServiceChargeVO>> page(ServiceChargeQueryDTO serviceChargeQueryDTO) {
        IPage<ServiceChargeVO> iPage = serviceChargeService.page(serviceChargeQueryDTO);
        return Result.success(iPage);
    }
}