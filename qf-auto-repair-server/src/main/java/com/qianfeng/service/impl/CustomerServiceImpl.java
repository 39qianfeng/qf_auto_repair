package com.qianfeng.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qianfeng.dto.CustomerDTO;
import com.qianfeng.dto.CustomerQueryDTO;
import com.qianfeng.entity.Customer;
import com.qianfeng.entity.RepairOrder;
import com.qianfeng.entity.Vehicle;
import com.qianfeng.mapper.CustomerMapper;
import com.qianfeng.mapper.RepairOrderMapper;
import com.qianfeng.mapper.VehicleMapper;
import com.qianfeng.service.CustomerService;
import com.qianfeng.vo.CustomerVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private VehicleMapper vehicleMapper;
    @Autowired
    private RepairOrderMapper repairOrderMapper;

    @Override
    public List<CustomerVO> getAllCustomers() {
        List<Customer> customers = customerMapper.selectList(null);
        return customers.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    public CustomerVO getCustomerById(Integer id) {
        Customer customer = customerMapper.selectById(id);
        return convertToVO(customer);
    }

    @Override
    public CustomerVO createCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        customerMapper.insert(customer);
        return convertToVO(customer);
    }

    @Override
    public CustomerVO updateCustomer(Integer id, CustomerDTO customerDTO) {
        Customer customer = customerMapper.selectById(id);
        if(customer == null){
            log.info("customer为空,更新失败");
            return null;
        }
        BeanUtils.copyProperties(customerDTO, customer);
        customerMapper.updateById(customer);
        return convertToVO(customer);
    }

    @Override
    public void deleteCustomer(Integer id) {
        customerMapper.deleteById(id);
    }

    @Override
    public IPage<CustomerVO> pageQuery(CustomerQueryDTO customerQueryDTO) {
        //使用mp进行分页查询n
        // 创建分页对象
        int current = customerQueryDTO.getCurrent();
        int size = customerQueryDTO.getSize();
        IPage<Customer> page = new Page<>(current, size);
        // 创建查询条件
        LambdaQueryWrapper<Customer> queryWrapper = new LambdaQueryWrapper<>();
        String name = customerQueryDTO.getName();
        if (name != null && !name.isEmpty()) {
            queryWrapper.like(Customer::getName, name);
        }
        // 获取到了分页查询数据
        IPage<Customer> customerIPage = customerMapper.selectPage(page, queryWrapper);
        //根据查询到的顾客ID查询其待维修车辆数量、维修订单数。
        // 转换为 CustomerVO 并添加额外字段
        IPage<CustomerVO> voPage = customerIPage.convert(customer -> {
            CustomerVO vo = new CustomerVO();
            BeanUtils.copyProperties(customer, vo);

            // 查询客户的车辆数量
            vo.setVehicleCount(vehicleMapper.selectCount(new LambdaQueryWrapper<Vehicle>().eq(Vehicle::getCustomerId, customer.getCustomerId())));

            // 查询客户的维修订单数量
            vo.setRepairOrderCount(repairOrderMapper.selectCount(new LambdaQueryWrapper<RepairOrder>().eq(RepairOrder::getCustomerId, customer.getCustomerId())));

            return vo;
        });
        // 返回分页查询数据
        return voPage;
    }

    private CustomerVO convertToVO(Customer customer) {
        CustomerVO vo = new CustomerVO();
        BeanUtils.copyProperties(customer, vo);
        // 可以在这里添加额外的字段转换逻辑
        return vo;
    }
}