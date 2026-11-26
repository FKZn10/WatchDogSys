package com.jpmsoft.projectConsumer.controller.v1.projectOutline;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.projectOutline.OutlineProjectTempRequest;
import com.jpmsoft.projectConsumer.feign.OutlineFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 项目管理大纲体系-关联业务功能
 *
 * @author tianhelin
 * @date 2025/6/11
 */
@Slf4j
@RestController
@Api(value = "项目管理大纲体系-关联业务功能", tags = "项目管理大纲体系-关联业务功能")
@RequestMapping("/v1/projectOutline/projectTemp")
public class OutlineProjectTempController {
    @Resource
    private OutlineFeign outlineFeign;

    /**
     * 新增项目管理大纲体系-关联业务功能
     *
     * @param request
     * @return
     */
    @ApiOperation("新增项目管理大纲体系-关联业务功能")
    @PostMapping("/createOutlineProjectTemp")
    public BaseResponse createOutlineProjectTemp(@RequestBody OutlineProjectTempRequest request, @RequestHeader String token) {
        log.info("createOutlineProjectTemp");
        return outlineFeign.createOutlineProjectTemp(request, token);
    }

    /**
     * 删除项目管理大纲体系-关联业务功能
     *
     * @param id
     * @return
     */
    @ApiOperation("删除项目管理大纲体系-关联业务功能")
    @GetMapping("/deleteOutlineProjectTempById")
    public BaseResponse deleteOutlineProjectTempById(@RequestParam("id") String id, @RequestHeader String token) {
        log.info("deleteOutlineProjectTempById");
        return outlineFeign.deleteOutlineProjectTempById(id, token);
    }

    /**
     * 修改项目管理大纲体系-关联业务功能
     *
     * @param request
     * @return
     */
    @ApiOperation("修改项目管理大纲体系-关联业务功能")
    @PostMapping("/updateOutlineProjectTemp")
    public BaseResponse updateOutlineProjectTemp(@RequestBody OutlineProjectTempRequest request, @RequestHeader String token) {
        log.info("updateOutlineProjectTemp");
        return outlineFeign.updateOutlineProjectTemp(request, token);
    }

    /**
     * 查询项目大类
     *
     * @param id
     * @return
     */
    @ApiOperation("查询项目管理大纲体系-关联业务功能")
    @GetMapping("/getOutlineProjectTempById")
    public BaseResponse getOutlineProjectTempById(@RequestParam("id") String id) {
        log.info("getOutlineProjectTempById");
        return outlineFeign.getOutlineProjectTempById(id);
    }

    /**
     * 分页查询项目大类
     *
     * @param request
     * @return
     */
    @ApiOperation("分页查询项目管理大纲体系-关联业务功能")
    @PostMapping("/getOutlineProjectTempPage")
    public BaseResponse getOutlineProjectTempPage(@RequestBody OutlineProjectTempRequest request) {
        log.info("getOutlineProjectTempPage");
        return outlineFeign.getOutlineProjectTempPage(request);
    }

    /**
     * 自动保存项目管理大纲体系-关联业务功能
     *
     * @param request
     * @param token
     * @return
     */
    @ApiOperation("自动保存项目管理大纲体系-关联业务功能")
    @PostMapping("/autoSaveOutlineProjectTemp")
    public BaseResponse autoSaveOutlineProjectTemp(@RequestBody OutlineProjectTempRequest request, @RequestHeader String token) {
        log.info("autoSaveOutlineProjectTemp");
        return outlineFeign.autoSaveOutlineProjectTemp(request, token);
    }
}
