package com.jpmsoft.projectConsumer.controller.v1.projectOutline;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.projectOutline.OutlineProjectStyleRequest;
import com.jpmsoft.projectConsumer.feign.OutlineFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 管理规划子系统 样式
 *
 * @author tianhelin
 * @date 2025/8/18
 */
@Slf4j
@Api(value = "项目管理大纲样式", tags = "项目管理大纲样式")
@RestController
@RequestMapping("/v1/projectOutline/style")
public class OutlineProjectStyleController {
    @Resource
    private OutlineFeign outlineFeign;

    /**
     * 获取体系模板样式分页列表
     *
     * @param request
     * @return
     */
    @ApiOperation("获取体系模板样式分页列表")
    @PostMapping("/getOutlineStylePage")
    public BaseResponse getOutlineStylePage(@RequestBody OutlineProjectStyleRequest request) {
        log.info("getOutlineStylePage}");
        return outlineFeign.getOutlineStylePage(request);
    }

    /**
     * 根据ID获取体系模板样式
     *
     * @param id
     * @return
     */
    @ApiOperation("根据ID获取体系模板样式")
    @GetMapping("/getOutlineStyleById")
    public BaseResponse getOutlineStyleById(@RequestParam String id) {
        log.info("getOutlineStyleById");
        return outlineFeign.getOutlineStyleById(id);
    }

    /**
     * 删除体系模板样式
     *
     * @param request
     * @return
     */
    @ApiOperation("删除体系模板样式")
    @PostMapping("/deleteOutlineStyleById")
    public BaseResponse deleteOutlineStyleById(@RequestBody OutlineProjectStyleRequest request) {
        log.info("deleteOutlineStyleById");
        return outlineFeign.deleteOutlineStyleById(request);
    }

    /**
     * 创建或更新体系模板样式
     *
     * @param request
     * @param token
     * @return
     */
    @ApiOperation("创建或更新体系模板样式")
    @PostMapping("/createOrUpdateOutlineStyle")
    public BaseResponse createOrUpdateOutlineStyle(@RequestBody OutlineProjectStyleRequest request, @RequestHeader String token) {
        log.info("createOrUpdateOutlineStyle");
        return outlineFeign.createOrUpdateOutlineStyle(request, token);
    }

    /**
     * 初始化样式数据
     *
     * @param token
     * @return
     */
    @ApiOperation("初始化样式数据")
    @GetMapping("/initializationOutlineStyle")
    public BaseResponse initializationOutlineStyle(@RequestHeader String token) {
        log.info("initializationOutlineStyle");
        return outlineFeign.initializationOutlineStyle(token);
    }
}