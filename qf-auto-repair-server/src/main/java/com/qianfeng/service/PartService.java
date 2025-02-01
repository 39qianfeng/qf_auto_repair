package com.qianfeng.service;

import com.qianfeng.dto.PartDTO;
import com.qianfeng.vo.PartVO;
import java.util.List;

public interface PartService {
    List<PartVO> getAllParts();
    PartVO getPartById(Integer id);
    PartVO createPart(PartDTO partDTO);
    PartVO updatePart(Integer id, PartDTO partDTO);
    void deletePart(Integer id);
}