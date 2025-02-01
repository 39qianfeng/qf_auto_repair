package com.qianfeng.service.impl;

import com.qianfeng.dto.FinanceDTO;
import com.qianfeng.entity.Finance;
import com.qianfeng.mapper.FinanceMapper;
import com.qianfeng.service.FinanceService;
import com.qianfeng.vo.FinanceVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FinanceServiceImpl implements FinanceService {

    @Autowired
    private FinanceMapper financeMapper;

    @Override
    public List<FinanceVO> getAllFinanceRecords() {
        List<Finance> financeRecords = financeMapper.selectList(null);
        return financeRecords.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    public FinanceVO getFinanceRecordById(Integer id) {
        Finance financeRecord = financeMapper.selectById(id);
        return convertToVO(financeRecord);
    }

    @Override
    public FinanceVO createFinanceRecord(FinanceDTO financeDTO) {
        Finance financeRecord = new Finance();
        BeanUtils.copyProperties(financeDTO, financeRecord);
        financeMapper.insert(financeRecord);
        return convertToVO(financeRecord);
    }

    @Override
    public FinanceVO updateFinanceRecord(Integer id, FinanceDTO financeDTO) {
        Finance financeRecord = financeMapper.selectById(id);
        BeanUtils.copyProperties(financeDTO, financeRecord);
        financeMapper.updateById(financeRecord);
        return convertToVO(financeRecord);
    }

    @Override
    public void deleteFinanceRecord(Integer id) {
        financeMapper.deleteById(id);
    }

    private FinanceVO convertToVO(Finance finance) {
        FinanceVO vo = new FinanceVO();
        BeanUtils.copyProperties(finance, vo);
        // 可以在这里添加额外的字段转换逻辑
        return vo;
    }
}