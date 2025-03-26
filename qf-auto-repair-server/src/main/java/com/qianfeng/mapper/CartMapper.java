package com.qianfeng.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qianfeng.entity.CartItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CartMapper extends BaseMapper<CartItem> {
    // 自定义查询方法：根据客户 ID 查询购物车商品
//    List<CartItem> selectByCustomerId(Long customerId);
//
//    // 自定义删除方法：清空客户购物车
//    void deleteByCustomerId(Long customerId);
}