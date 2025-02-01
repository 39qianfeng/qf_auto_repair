package com.qianfeng.service.impl;

import com.qianfeng.dto.SupplierDTO;
import com.qianfeng.entity.Supplier;
import com.qianfeng.mapper.SupplierMapper;
import com.qianfeng.service.SupplierService;
import com.qianfeng.vo.SupplierVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierMapper supplierMapper;

    @Override
    public List<SupplierVO> getAllSuppliers() {
        List<Supplier> suppliers = supplierMapper.selectList(null);
        return suppliers.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    public SupplierVO getSupplierById(Integer id) {
        Supplier supplier = supplierMapper.selectById(id);
        return convertToVO(supplier);
    }

    @Override
    public SupplierVO createSupplier(SupplierDTO supplierDTO) {
        Supplier supplier = new Supplier();
        BeanUtils.copyProperties(supplierDTO, supplier);
        supplierMapper.insert(supplier);
        return convertToVO(supplier);
    }

    @Override
    public SupplierVO updateSupplier(Integer id, SupplierDTO supplierDTO) {
        Supplier supplier = supplierMapper.selectById(id);
        BeanUtils.copyProperties(supplierDTO, supplier);
        supplierMapper.updateById(supplier);
        return convertToVO(supplier);
    }

    @Override
    public void deleteSupplier(Integer id) {
        supplierMapper.deleteById(id);
    }

    private SupplierVO convertToVO(Supplier supplier) {
        SupplierVO vo = new SupplierVO();
        BeanUtils.copyProperties(supplier, vo);
        // 可以在这里添加额外的字段转换逻辑
        return vo;
    }
}