package com.jpmsoft.projectConsumer.controller.v1.projectOutline;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.projectOutline.OutlineLibRiskRequest;
import com.jpmsoft.projectConsumer.feign.OutlineFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 体系模板管理风险指标
 */
@Slf4j
@RestController
@Api(value = "项目管理大纲体系模板-风险指标", tags = "项目管理大纲体系模板-风险指标")
@RequestMapping("/v1/projectOutline/libRisk")
public class OutlineLibRiskController {
    @Resource
    private OutlineFeign outlineFeign;

    @ApiOperation("新增体系模板-风险指标")
    @PostMapping("/createOutlineLibRisk")
    public BaseResponse createOutlineLibRisk(@RequestBody OutlineLibRiskRequest request, @RequestHeader String token) {
        log.info("createOutlineLibRisk");
        return outlineFeign.createOutlineLibRisk(request, token);
    }

    @ApiOperation("根据体系模板节点ID查询所有风险指标")
    @GetMapping("/getOutlineLibRiskByOslId")
    public BaseResponse getOutlineLibRiskByOslId(@RequestParam String oslId) {
        log.info("getOutlineLibRiskByOslId");
        return outlineFeign.getOutlineLibRiskByOslId(oslId);
    }

    @ApiOperation("根据ID查询风险指标")
    @GetMapping("/getOutlineLibRiskById")
    public BaseResponse getOutlineLibRiskById(@RequestParam String id) {
        log.info("getOutlineLibRiskById");
        return outlineFeign.getOutlineLibRiskById(id);
    }

    @ApiOperation("分页查询风险指标")
    @PostMapping("/getOutlineLibRiskPage")
    public BaseResponse getOutlineLibRiskPage(@RequestBody OutlineLibRiskRequest request) {
        log.info("getOutlineLibRiskPage");
        return outlineFeign.getOutlineLibRiskPage(request);
    }

    @ApiOperation("根据ID删除风险指标")
    @GetMapping("/deleteOutlineLibRiskById")
    public BaseResponse deleteOutlineLibRiskById(@RequestParam String id, @RequestHeader String token) {
        log.info("deleteOutlineLibRiskById");
        return outlineFeign.deleteOutlineLibRiskById(id, token);
    }

    @ApiOperation("更新体系模板-风险指标")
    @PostMapping("/updateOutlineLibRisk")
    public BaseResponse updateOutlineLibRisk(@RequestBody OutlineLibRiskRequest request, @RequestHeader String token) {
        log.info("updateOutlineLibRisk");
        return outlineFeign.updateOutlineLibRisk(request, token);
    }

    @ApiOperation("自动保存风险指标")
    @PostMapping("/autoSaveOutlineLibRisk")
    public BaseResponse autoSaveOutlineLibRisk(@RequestBody OutlineLibRiskRequest request, @RequestHeader String token) {
        log.info("autoSaveOutlineLibRisk");
        return outlineFeign.autoSaveOutlineLibRisk(request, token);
    }
}
