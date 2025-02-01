package com.qianfeng.service;

import com.qianfeng.dto.SupplierDTO;
import com.qianfeng.vo.SupplierVO;
import java.util.List;

public interface SupplierService {
    List<SupplierVO> getAllSuppliers();
    SupplierVO getSupplierById(Integer id);
    SupplierVO createSupplier(SupplierDTO supplierDTO);
    SupplierVO updateSupplier(Integer id, SupplierDTO supplierDTO);
    void deleteSupplier(Integer id);
}