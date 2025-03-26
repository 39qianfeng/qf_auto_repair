package com.qianfeng.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qianfeng.entity.RepairOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Mapper
public interface RepairOrderMapper extends BaseMapper<RepairOrder> {
    IPage<RepairOrder> selectPageBySearchKey(IPage<RepairOrder> page,
                                            @Param("orderId") Integer orderId,
                                            @Param("startDate") LocalDateTime startDate,
                                            @Param("endDate") LocalDateTime endDate,
                                            @Param("searchKey") String searchKey);

    BigDecimal getTotalAmount(Map map);
    // 这里可以添加自定义的查询方法（如果需要）
}