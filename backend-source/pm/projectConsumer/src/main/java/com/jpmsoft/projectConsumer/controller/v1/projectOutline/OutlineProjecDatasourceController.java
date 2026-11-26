package com.jpmsoft.projectConsumer.controller.v1.projectOutline;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.projectOutline.OutlineProjecDatasourceRequest;
import com.jpmsoft.projectConsumer.feign.OutlineFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 项目管理大纲体系节点数据源配置
 *
 * @author tianhelin
 * @date 2025/6/17
 */
@Slf4j
@RestController
@Api(value = "项目管理大纲体系-节点数据源配置", tags = "项目管理大纲体系-节点数据源配置")
@RequestMapping("/v1/projectOutline/projectDatasource")
public class OutlineProjecDatasourceController {
    @Resource
    private OutlineFeign outlineFeign;

    /**
     * 新增项目管理大纲体系节点数据源配置
     *
     * @param request
     * @param token
     * @return
     */
    @ApiOperation("新增项目管理大纲体系节点数据源配置")
    @PostMapping("/createProjectDatasource")
    public BaseResponse createProjectDatasource(@RequestBody OutlineProjecDatasourceRequest request, @RequestHeader String token) {
        log.info("createProjectDatasource");
        return outlineFeign.createProjectDatasource(request, token);
    }

    /**
     * 删除项目管理大纲体系节点数据源配置
     *
     * @param id
     * @param token
     * @return
     */
    @ApiOperation("删除项目管理大纲体系节点数据源配置")
    @GetMapping("/deleteProjectDatasourceById")
    public BaseResponse deleteProjectDatasourceById(@RequestParam("id") String id, @RequestHeader String token) {
        log.info("deleteProjectDatasourceById");
        return outlineFeign.deleteProjectDatasourceById(id, token);
    }

    /**
     * 修改项目管理大纲体系节点数据源配置
     *
     * @param request
     * @return
     */
    @ApiOperation("修改项目管理大纲体系节点数据源配置")
    @PostMapping("/updateProjectDatasource")
    public BaseResponse updateProjectDatasource(@RequestBody OutlineProjecDatasourceRequest request, @RequestHeader String token) {
        log.info("updateProjectDatasource");
        return outlineFeign.updateProjectDatasource(request, token);
    }

    /**
     * 获取项目管理大纲体系节点数据源配置
     *
     * @param id
     * @return
     */
    @ApiOperation("获取项目管理大纲体系节点数据源配置")
    @GetMapping("/getProjectDatasourceById")
    public BaseResponse getProjectDatasourceById(@RequestParam("id") String id) {
        log.info("getProjectDatasourceById");
        return outlineFeign.getProjectDatasourceById(id);
    }

    /**
     * 分页查询项目管理大纲体系节点数据源配置
     *
     * @param request
     * @return
     */
    @ApiOperation("分页查询项目管理大纲体系节点数据源配置")
    @PostMapping("/getProjectDatasourcePage")
    public BaseResponse getProjectDatasourcePage(@RequestBody OutlineProjecDatasourceRequest request) {
        log.info("getProjectDatasourcePage");
        return outlineFeign.getProjectDatasourcePage(request);
    }

    /**
     * 自动保存项目管理大纲体系节点数据源配置
     *
     * @param request
     * @param token
     * @return
     */
    @ApiOperation("自动保存项目管理大纲体系节点数据源配置")
    @PostMapping("/autoSaveProjectDatasource")
    public BaseResponse autoSaveProjectDatasource(@RequestBody OutlineProjecDatasourceRequest request, @RequestHeader String token) {
        log.info("autoSaveProjectDatasource");
        return outlineFeign.autoSaveProjectDatasource(request, token);
    }
}