package com.qianfeng.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qianfeng.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单详情 Mapper 接口
 */
@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {
    // MyBatis-Plus 已经提供了基础的 CRUD 方法，无需额外定义
    // 如果需要自定义查询方法，可以在这里添加
}