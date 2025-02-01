package com.qianfeng.service;

import com.qianfeng.dto.EmployeeDTO;
import com.qianfeng.vo.EmployeeVO;
import java.util.List;

public interface EmployeeService {
    List<EmployeeVO> getAllEmployees();
    EmployeeVO getEmployeeById(Integer id);
    EmployeeVO createEmployee(EmployeeDTO employeeDTO);
    EmployeeVO updateEmployee(Integer id, EmployeeDTO employeeDTO);
    void deleteEmployee(Integer id);
}