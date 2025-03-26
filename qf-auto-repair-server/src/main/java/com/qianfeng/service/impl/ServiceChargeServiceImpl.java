package com.qianfeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qianfeng.dto.ServiceChargeDTO;
import com.qianfeng.dto.ServiceChargeQueryDTO;
import com.qianfeng.entity.ServiceCharge;
import com.qianfeng.mapper.ServiceChargeMapper;
import com.qianfeng.service.ServiceChargeService;
import com.qianfeng.vo.ServiceChargeVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceChargeServiceImpl implements ServiceChargeService {

    @Autowired
    private ServiceChargeMapper serviceChargeMapper;

    @Override
    public List<ServiceChargeVO> getAllServiceCharges() {
        List<ServiceCharge> serviceCharges = serviceChargeMapper.selectList(null);
        return serviceCharges.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    public ServiceChargeVO getServiceChargeById(Integer id) {
        ServiceCharge serviceCharge = serviceChargeMapper.selectById(id);
        return convertToVO(serviceCharge);
    }

    @Override
    public ServiceChargeVO createServiceCharge(ServiceChargeDTO serviceChargeDTO) {
        ServiceCharge serviceCharge = new ServiceCharge();
        BeanUtils.copyProperties(serviceChargeDTO, serviceCharge);
        serviceChargeMapper.insert(serviceCharge);
        return convertToVO(serviceCharge);
    }

    @Override
    public ServiceChargeVO updateServiceCharge(Integer id, ServiceChargeDTO serviceChargeDTO) {
        ServiceCharge serviceCharge = serviceChargeMapper.selectById(id);
        BeanUtils.copyProperties(serviceChargeDTO, serviceCharge);
        serviceChargeMapper.updateById(serviceCharge);
        return convertToVO(serviceCharge);
    }

    @Override
    public void deleteServiceCharge(Integer id) {
        serviceChargeMapper.deleteById(id);
    }

    @Override
    public IPage<ServiceChargeVO> page(ServiceChargeQueryDTO serviceChargeQueryDTO) {
        Integer current = serviceChargeQueryDTO.getCurrent();
        Integer size = serviceChargeQueryDTO.getSize();
        IPage<ServiceCharge> page = new Page<>(current, size);

        LambdaQueryWrapper<ServiceCharge> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//        if (serviceChargeQueryDTO.getServiceName() != null) {
//            lambdaQueryWrapper.like(ServiceCharge::getServiceName, serviceChargeQueryDTO.getServiceName());
//        }
        lambdaQueryWrapper.orderByDesc(ServiceCharge::getUpdateTime);

        IPage<ServiceCharge> iPage = serviceChargeMapper.selectPage(page, lambdaQueryWrapper);
//        iPage.convert(serviceCharge -> {
//            ServiceChargeVO vo = convertToVO(serviceCharge);
//
//            return vo;
//        })
        return iPage.convert(this::convertToVO);
    }

    private ServiceChargeVO convertToVO(ServiceCharge serviceCharge) {
        if (serviceCharge == null) return null;
        ServiceChargeVO vo = new ServiceChargeVO();
        BeanUtils.copyProperties(serviceCharge, vo);
        // 可以在这里添加额外的字段转换逻辑
        return vo;
    }
}