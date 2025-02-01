package com.qianfeng.service.impl;

import com.qianfeng.dto.PartDTO;
import com.qianfeng.entity.Part;
import com.qianfeng.mapper.PartMapper;
import com.qianfeng.service.PartService;
import com.qianfeng.vo.PartVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PartServiceImpl implements PartService {

    @Autowired
    private PartMapper partMapper;

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

    private PartVO convertToVO(Part part) {
        PartVO vo = new PartVO();
        BeanUtils.copyProperties(part, vo);
        // 可以在这里添加额外的字段转换逻辑
        return vo;
    }
}