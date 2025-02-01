package com.qianfeng.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qianfeng.dto.CustomerDTO;
import com.qianfeng.dto.CustomerQueryDTO;
import com.qianfeng.result.PageResult;
import com.qianfeng.vo.CustomerVO;
import java.util.List;

public interface CustomerService {
    List<CustomerVO> getAllCustomers();
    CustomerVO getCustomerById(Integer id);
    CustomerVO createCustomer(CustomerDTO customerDTO);
    CustomerVO updateCustomer(Integer id, CustomerDTO customerDTO);
    void deleteCustomer(Integer id);

    IPage<CustomerVO> pageQuery(CustomerQueryDTO customerQueryDTO);
}