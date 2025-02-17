package com.qianfeng.service;

import com.qianfeng.dto.EmployeeDTO;
import com.qianfeng.dto.EmployeeLoginDTO;
import com.qianfeng.exception.AccountNotFoundException;
import com.qianfeng.vo.EmployeeVO;


import java.util.List;

public interface EmployeeService {
    EmployeeVO login(EmployeeLoginDTO employeeLoginDTO) throws AccountNotFoundException;
    List<EmployeeVO> getAllEmployees();
    EmployeeVO getEmployeeById(Integer id);
    EmployeeVO createEmployee(EmployeeDTO employeeDTO);
    EmployeeVO updateEmployee(Integer id, EmployeeDTO employeeDTO);
    void deleteEmployee(Integer id);
}