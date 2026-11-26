package com.jpmsoft.projectConsumer.controller.v1.projectOutline;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.projectOutline.OutlineProjectRiskRequest;
import com.jpmsoft.projectConsumer.feign.OutlineFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 项目管理大纲体系管理风险指标
 *
 * @author tianhelin
 * @date 2025/6/17
 */
@Slf4j
@RestController
@Api(value = "项目管理大纲体系管理-风险指标", tags = "项目管理大纲体系管理-风险指标")
@RequestMapping("/v1/projectOutline/projectRisk")
public class OutlineProjectRiskController {
    @Resource
    private OutlineFeign outlineFeign;

    /**
     * 新增项目管理大纲体系管理风险指标
     *
     * @param request
     * @param token
     * @return
     */
    @ApiOperation(value = "新增项目管理大纲体系管理风险指标")
    @PostMapping("/createOutlineProjectRisk")
    public BaseResponse createOutlineProjectRisk(@RequestBody OutlineProjectRiskRequest request, @RequestHeader String token) {
        log.info("createOutlineProjectRisk");
        return outlineFeign.createOutlineProjectRisk(request, token);
    }

    /**
     * 删除项目管理大纲体系管理风险指标
     *
     * @param id
     * @param token
     * @return
     */
    @ApiOperation("删除项目管理大纲体系管理风险指标")
    @GetMapping("/deleteOutlineProjectRiskById")
    public BaseResponse deleteOutlineProjectRiskById(@RequestParam("id") String id, @RequestHeader String token) {
        log.info("deleteOutlineProjectRiskById");
        return outlineFeign.deleteOutlineProjectRiskById(id, token);
    }

    /**
     * 修改项目管理大纲体系管理风险指标
     *
     * @param request
     * @param token
     * @return
     */
    @ApiOperation("修改项目管理大纲体系管理风险指标")
    @PostMapping("/updateOutlineProjectRisk")
    public BaseResponse updateOutlineProjectRisk(@RequestBody OutlineProjectRiskRequest request, @RequestHeader String token) {
        log.info("updateOutlineProjectRisk");
        return outlineFeign.updateOutlineProjectRisk(request, token);
    }

    /**
     * 查询项目管理大纲体系管理风险指标
     *
     * @param id
     * @return
     */
    @ApiOperation("查询项目管理大纲体系管理风险指标")
    @GetMapping("/getOutlineProjectRiskById")
    public BaseResponse getOutlineProjectRiskById(@RequestParam("id") String id) {
        log.info("getOutlineProjectRiskById");
        return outlineFeign.getOutlineProjectRiskById(id);
    }

    /**
     * 分页查询项目管理大纲体系管理风险指标
     *
     * @param request
     * @return
     */
    @ApiOperation("分页查询项目管理大纲体系管理风险指标")
    @PostMapping("/getOutlineProjectRiskPage")
    public BaseResponse getOutlineProjectRiskPage(@RequestBody OutlineProjectRiskRequest request) {
        log.info("getOutlineProjectRiskPage");
        return outlineFeign.getOutlineProjectRiskPage(request);
    }

    /**
     * 自动保存项目管理大纲体系管理风险指标
     *
     * @param request
     * @param token
     * @return
     */
    @ApiOperation("自动保存项目管理大纲体系管理风险指标")
    @PostMapping("/autoSaveOutlineProjectRisk")
    public BaseResponse autoSaveOutlineProjectRisk(@RequestBody OutlineProjectRiskRequest request, @RequestHeader String token) {
        log.info("autoSaveOutlineProjectRisk");
        return outlineFeign.autoSaveOutlineProjectRisk(request, token);
    }

    /**
     * 根据项目管理大纲id获取项目风险指标
     *
     * @param opmId
     * @return
     */
    @ApiOperation("根据项目管理大纲id获取项目风险指标")
    @GetMapping("/getOutlineProjectRiskListByOpmId")
    public BaseResponse getOutlineProjectRiskListByOpmId(@RequestParam("opmId") String opmId) {
        log.info("getOutlineProjectRiskListByOpmId");
        return outlineFeign.getOutlineProjectRiskListByOpmId(opmId);
    }
}