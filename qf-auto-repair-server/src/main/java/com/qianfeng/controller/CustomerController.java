package com.qianfeng.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qianfeng.dto.CustomerDTO;
import com.qianfeng.dto.CustomerQueryDTO;
import com.qianfeng.result.PageResult;
import com.qianfeng.result.Result;
import com.qianfeng.service.CustomerService;
import com.qianfeng.vo.CustomerVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@Api(tags = "顾客相关接口")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    @ApiOperation("获取所有顾客信息")
    public Result<List<CustomerVO>> getAllCustomers() {
        List<CustomerVO> customers = customerService.getAllCustomers();
        return Result.success(customers);
    }

    @GetMapping("/{id}")
    public Result<CustomerVO> getCustomerById(@PathVariable Integer id) {
        CustomerVO customer = customerService.getCustomerById(id);
        return Result.success(customer);
    }

    @PostMapping
    public Result<CustomerVO> createCustomer(@RequestBody CustomerDTO customerDTO) {
        CustomerVO customer = customerService.createCustomer(customerDTO);
        return Result.success(customer);
    }

    @PutMapping("/update/{id}")
    public Result<CustomerVO> updateCustomer(@PathVariable Integer id, @RequestBody CustomerDTO customerDTO) {
        CustomerVO customer = customerService.updateCustomer(id, customerDTO);
        if(customer == null){
            return Result.error("查询客户Id不存在");
        }
        return Result.success(customer);
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteCustomer(@PathVariable Integer id) {
        customerService.deleteCustomer(id);
        return Result.success();
    }

    @GetMapping("/pageByName")
    public Result<IPage<CustomerVO>> pageByName(CustomerQueryDTO customerQueryDTO){
        System.out.println(customerQueryDTO);
        IPage<CustomerVO> iPage = customerService.pageQuery(customerQueryDTO);
        System.out.println(iPage.getRecords().toString());
        return Result.success(iPage);
    }

    @GetMapping("/page")
    public Result<IPage<CustomerVO>> page(CustomerQueryDTO customerQueryDTO){
        System.out.println(customerQueryDTO);
        IPage<CustomerVO> iPage = customerService.pageQuery(customerQueryDTO);
        System.out.println(iPage.getRecords().toString());
        return Result.success(iPage);
    }
}
