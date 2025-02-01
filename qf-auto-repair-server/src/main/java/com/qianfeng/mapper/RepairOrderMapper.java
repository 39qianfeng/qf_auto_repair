package com.qianfeng.mapper;

import com.qianfeng.entity.RepairOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RepairOrderMapper extends BaseMapper<RepairOrder> {
    // 这里可以添加自定义的查询方法（如果需要）
}