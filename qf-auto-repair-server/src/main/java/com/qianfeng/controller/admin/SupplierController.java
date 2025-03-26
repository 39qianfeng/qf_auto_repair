package com.qianfeng.controller.admin;

import com.qianfeng.dto.SupplierDTO;
import com.qianfeng.result.Result;
import com.qianfeng.service.SupplierService;
import com.qianfeng.vo.SupplierVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
@Api(tags = "供应商信息相关接口")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @GetMapping
    public Result<List<SupplierVO>> getAllSuppliers() {
        List<SupplierVO> suppliers = supplierService.getAllSuppliers();
        return Result.success(suppliers);
    }

    @GetMapping("/{id}")
    public Result<SupplierVO> getSupplierById(@PathVariable Integer id) {
        SupplierVO supplier = supplierService.getSupplierById(id);
        return Result.success(supplier);
    }

    @PostMapping
    public Result<SupplierVO> createSupplier(@RequestBody SupplierDTO supplierDTO) {
        SupplierVO supplier = supplierService.createSupplier(supplierDTO);
        return Result.success(supplier);
    }

    @PutMapping("/{id}")
    public Result<SupplierVO> updateSupplier(@PathVariable Integer id, @RequestBody SupplierDTO supplierDTO) {
        SupplierVO supplier = supplierService.updateSupplier(id, supplierDTO);
        return Result.success(supplier);
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteSupplier(@PathVariable Integer id) {
        supplierService.deleteSupplier(id);
        return Result.success();
    }
}