package com.qianfeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qianfeng.constant.MessageConstant;
import com.qianfeng.constant.StatusConstant;
import com.qianfeng.dto.EmployeeDTO;
import com.qianfeng.dto.EmployeeLoginDTO;
import com.qianfeng.entity.Employee;
import com.qianfeng.exception.AccountLockedException;
import com.qianfeng.exception.AccountNotFoundException;
import com.qianfeng.exception.PasswordErrorException;
import com.qianfeng.mapper.EmployeeMapper;
import com.qianfeng.service.EmployeeService;
import com.qianfeng.vo.EmployeeVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */

    @Override
    public EmployeeVO login(EmployeeLoginDTO employeeLoginDTO){
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Employee::getUsername,employeeLoginDTO.getUsername());
        Employee employee = employeeMapper.selectOne(lambdaQueryWrapper);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        //对前端传过来的明文密码进行md5加密处理
//        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        EmployeeVO employeeVO = convertToVO(employee);
        return employeeVO;
    }

    /**
     * 获取所有员工信息
     * @return
     */
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