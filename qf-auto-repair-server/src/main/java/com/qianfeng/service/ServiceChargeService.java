package com.qianfeng.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qianfeng.dto.ServiceChargeDTO;
import com.qianfeng.dto.ServiceChargeQueryDTO;
import com.qianfeng.entity.ServiceCharge;
import com.qianfeng.vo.ServiceChargeVO;

import java.util.List;

public interface ServiceChargeService{

    List<ServiceChargeVO> getAllServiceCharges();

    ServiceChargeVO getServiceChargeById(Integer id);

    ServiceChargeVO createServiceCharge(ServiceChargeDTO serviceChargeDTO);

    ServiceChargeVO updateServiceCharge(Integer id, ServiceChargeDTO serviceChargeDTO);

    void deleteServiceCharge(Integer id);

    IPage<ServiceChargeVO> page(ServiceChargeQueryDTO serviceChargeQueryDTO);
}