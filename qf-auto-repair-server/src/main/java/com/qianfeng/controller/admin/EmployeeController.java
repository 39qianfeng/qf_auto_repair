package com.qianfeng.controller.admin;

import com.qianfeng.constant.JwtClaimsConstant;
import com.qianfeng.dto.EmployeeDTO;
import com.qianfeng.dto.EmployeeLoginDTO;
import com.qianfeng.properties.JwtProperties;
import com.qianfeng.result.Result;
import com.qianfeng.service.EmployeeService;
import com.qianfeng.utils.JwtUtil;
import com.qianfeng.vo.EmployeeLoginVO;
import com.qianfeng.vo.EmployeeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
@Api(tags = "员工相关接口")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    @PostMapping("/login")
    @ApiOperation(value = "员工登入")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO){
        log.info("员工登录：{}", employeeLoginDTO);

        EmployeeVO employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getEmployeeId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .employeeId(employee.getEmployeeId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .type("管理员")
                .build();

        return Result.success(employeeLoginVO);
    }
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