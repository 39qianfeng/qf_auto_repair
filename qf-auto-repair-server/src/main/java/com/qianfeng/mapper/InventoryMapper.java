package com.qianfeng.mapper;

import com.qianfeng.entity.Inventory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InventoryMapper extends BaseMapper<Inventory> {
    // 这里可以添加自定义的查询方法（如果需要）
}