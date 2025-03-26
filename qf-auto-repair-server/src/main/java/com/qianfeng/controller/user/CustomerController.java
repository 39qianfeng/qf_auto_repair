package com.qianfeng.controller.user;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qianfeng.constant.JwtClaimsConstant;
import com.qianfeng.dto.CustomerDTO;
import com.qianfeng.dto.CustomerLoginDTO;
import com.qianfeng.dto.CustomerQueryDTO;
import com.qianfeng.dto.EmployeeLoginDTO;
import com.qianfeng.properties.JwtProperties;
import com.qianfeng.result.Result;
import com.qianfeng.service.CustomerService;
import com.qianfeng.utils.JwtUtil;
import com.qianfeng.vo.CustomerLoginVO;
import com.qianfeng.vo.CustomerVO;
import com.qianfeng.vo.EmployeeLoginVO;
import com.qianfeng.vo.EmployeeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customer/customers")
@Api(tags = "顾客顾客相关接口")
@Slf4j
@Component("customerCustomerController")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private JwtProperties jwtProperties;
    @PostMapping("/login")
    @ApiOperation(value = "顾客登入")
    public Result<CustomerLoginVO> login(@RequestBody CustomerLoginDTO customerLoginDTO){
        log.info("顾客登录：{}", customerLoginDTO);

        CustomerLoginVO customerLoginVO = customerService.login(customerLoginDTO);

        return Result.success(customerLoginVO);
    }

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
