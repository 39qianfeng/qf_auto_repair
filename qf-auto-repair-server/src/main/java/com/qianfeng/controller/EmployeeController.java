package com.qianfeng.controller;

import com.qianfeng.dto.EmployeeDTO;
import com.qianfeng.result.Result;
import com.qianfeng.service.EmployeeService;
import com.qianfeng.vo.EmployeeVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@Api(tags = "员工相关接口")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public Result<List<EmployeeVO>> getAllEmployees() {
        List<EmployeeVO> employees = employeeService.getAllEmployees();
        return Result.success(employees);
    }

    @GetMapping("/{id}")
    public Result<EmployeeVO> getEmployeeById(@PathVariable Integer id) {
        EmployeeVO employee = employeeService.getEmployeeById(id);
        return Result.success(employee);
    }

    @PostMapping
    public Result<EmployeeVO> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        EmployeeVO employee = employeeService.createEmployee(employeeDTO);
        return Result.success(employee);
    }

    @PutMapping("/{id}")
    public Result<EmployeeVO> updateEmployee(@PathVariable Integer id, @RequestBody EmployeeDTO employeeDTO) {
        EmployeeVO employee = employeeService.updateEmployee(id, employeeDTO);
        return Result.success(employee);
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteEmployee(@PathVariable Integer id) {
        employeeService.deleteEmployee(id);
        return Result.success();
    }
}