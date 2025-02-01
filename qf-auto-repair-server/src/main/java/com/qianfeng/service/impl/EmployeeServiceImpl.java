package com.qianfeng.service.impl;

import com.qianfeng.dto.EmployeeDTO;
import com.qianfeng.entity.Employee;
import com.qianfeng.mapper.EmployeeMapper;
import com.qianfeng.service.EmployeeService;
import com.qianfeng.vo.EmployeeVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public List<EmployeeVO> getAllEmployees() {
        List<Employee> employees = employeeMapper.selectList(null);
        return employees.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    public EmployeeVO getEmployeeById(Integer id) {
        Employee employee = employeeMapper.selectById(id);
        return convertToVO(employee);
    }

    @Override
    public EmployeeVO createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        employeeMapper.insert(employee);
        return convertToVO(employee);
    }

    @Override
    public EmployeeVO updateEmployee(Integer id, EmployeeDTO employeeDTO) {
        Employee employee = employeeMapper.selectById(id);
        BeanUtils.copyProperties(employeeDTO, employee);
        employeeMapper.updateById(employee);
        return convertToVO(employee);
    }

    @Override
    public void deleteEmployee(Integer id) {
        employeeMapper.deleteById(id);
    }

    private EmployeeVO convertToVO(Employee employee) {
        EmployeeVO vo = new EmployeeVO();
        BeanUtils.copyProperties(employee, vo);
        // 可以在这里添加额外的字段转换逻辑
        return vo;
    }
}