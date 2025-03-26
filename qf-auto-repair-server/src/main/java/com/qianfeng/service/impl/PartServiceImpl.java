package com.qianfeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qianfeng.dto.PartDTO;
import com.qianfeng.dto.PartQueryDTO;
import com.qianfeng.entity.Part;
import com.qianfeng.entity.Supplier;
import com.qianfeng.mapper.PartMapper;
import com.qianfeng.mapper.SupplierMapper;
import com.qianfeng.service.PartService;
import com.qianfeng.vo.PartVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PartServiceImpl implements PartService {

    @Autowired
    private PartMapper partMapper;
    @Autowired
    private SupplierMapper supplierMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<PartVO> getAllParts() {
        List<Part> parts = partMapper.selectList(null);
        return parts.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    public PartVO getPartById(Integer id) {
        Part part = partMapper.selectById(id);
        return convertToVO(part);
    }

    @Override
    public PartVO createPart(PartDTO partDTO) {
        Part part = new Part();
        BeanUtils.copyProperties(partDTO, part);
        partMapper.insert(part);
        return convertToVO(part);
    }

    @Override
    public PartVO updatePart(Integer id, PartDTO partDTO) {
        Part part = partMapper.selectById(id);
        BeanUtils.copyProperties(partDTO, part);
        partMapper.updateById(part);
        return convertToVO(part);
    }

    @Override
    public void deletePart(Integer id) {
        partMapper.deleteById(id);
    }

    @Override
    public IPage<PartVO> page(PartQueryDTO partQueryDTO) {
        // 查询redis中是否存在零件数据
        String key = "part";
        IPage<PartVO> list = (IPage<PartVO>) redisTemplate.opsForValue().get(key);
        // 如果redis中存在零件数据则直接返回
        if(list != null && list.getTotal() > 0){
            log.info("Redis命中");
            return list;
        }
        Integer current = partQueryDTO.getCurrent();
        Integer size = partQueryDTO.getSize();
        IPage<Part> page = new Page<>(current,size);
//        partMapper.page(partQueryDTO);
        LambdaQueryWrapper<Part> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(Part::getUpdateTime);
        IPage<Part> iPage = partMapper.selectPage(page, lambdaQueryWrapper);
        IPage<PartVO> voIPage = iPage.convert(part -> {
            PartVO vo = convertToVO(part);
            Integer supplierId = vo.getSupplierId();
            if(supplierId != null){
                Supplier supplier = supplierMapper.selectById(supplierId);
                vo.setSupplierName(supplier.getName());
            }
            return vo;
        });
        redisTemplate.opsForValue().set(key,voIPage);
        return voIPage;
    }

    private PartVO convertToVO(Part part) {
        PartVO vo = new PartVO();
        BeanUtils.copyProperties(part, vo);
        // 可以在这里添加额外的字段转换逻辑
        return vo;
    }
}