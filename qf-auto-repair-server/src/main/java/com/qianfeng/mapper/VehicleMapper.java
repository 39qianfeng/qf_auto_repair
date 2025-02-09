package com.qianfeng.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qianfeng.entity.Vehicle;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface VehicleMapper extends BaseMapper<Vehicle> {
    // 这里可以添加自定义的查询方法（如果需要）
    // 联合查询方法
    IPage<Vehicle> selectPageWithCustomerType(IPage<Vehicle> page,
                                              @Param("vehicleId") Integer vehicleId,
                                              @Param("customerId") Integer customerId,
                                              @Param("customerType") String customerType,
                                              @Param("searchKey") String searchKey);
}