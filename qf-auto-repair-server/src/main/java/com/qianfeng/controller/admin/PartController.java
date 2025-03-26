package com.qianfeng.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qianfeng.dto.PartDTO;
import com.qianfeng.dto.PartQueryDTO;
import com.qianfeng.entity.Part;
import com.qianfeng.result.Result;
import com.qianfeng.service.PartService;
import com.qianfeng.vo.PartVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parts")
@Api(tags = "零件相关接口")
public class PartController {

    @Autowired
    private PartService partService;

    @GetMapping
    public Result<List<PartVO>> getAllParts() {
        List<PartVO> parts = partService.getAllParts();
        return Result.success(parts);
    }

    @GetMapping("/{id}")
    public Result<PartVO> getPartById(@PathVariable Integer id) {
        PartVO part = partService.getPartById(id);
        return Result.success(part);
    }

    @PostMapping
    public Result<PartVO> createPart(@RequestBody PartDTO partDTO) {
        PartVO part = partService.createPart(partDTO);
        return Result.success(part);
    }

    @PutMapping("/{id}")
    public Result<PartVO> updatePart(@PathVariable Integer id, @RequestBody PartDTO partDTO) {
        PartVO part = partService.updatePart(id, partDTO);
        return Result.success(part);
    }

    @DeleteMapping("/{id}")
    public Result<Void> deletePart(@PathVariable Integer id) {
        partService.deletePart(id);
        return Result.success();
    }

    /**
     * 分页查询
     */
    @GetMapping("/page")
    public Result<IPage<PartVO>> page(PartQueryDTO partQueryDTO){
        IPage<PartVO> iPage = partService.page(partQueryDTO);
        return Result.success(iPage);
    }
}