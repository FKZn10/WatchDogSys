package com.jpmsoft.projectProducer.controller.v1.projectOutline;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.projectOutline.OutlineProjecDatasourceRequest;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.service.projectOutline.IOutlineProjecDatasourceService;
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
@RequestMapping("/v1/projectOutline/projectDatasource")
public class OutlineProjecDatasourceController {
    @Resource
    private IOutlineProjecDatasourceService outlineProjecDatasourceService;

    /**
     * 新增项目管理大纲体系节点数据源配置
     *
     * @param request
     * @param token
     * @return
     */
    @PostMapping("/createProjectDatasource")
    public BaseResponse createProjectDatasource(@RequestBody OutlineProjecDatasourceRequest request, @RequestHeader String token) {
        log.info("createProjectDatasource");
        return outlineProjecDatasourceService.createProjectDatasource(request, token);
    }

    /**
     * 删除项目管理大纲体系节点数据源配置
     *
     * @param id
     * @param token
     * @return
     */
    @GetMapping("/deleteProjectDatasourceById")
    public BaseResponse deleteProjectDatasourceById(@RequestParam("id") String id, @RequestHeader String token) {
        log.info("deleteProjectDatasourceById");
        return outlineProjecDatasourceService.deleteProjectDatasourceById(id, token);
    }

    /**
     * 修改项目管理大纲体系节点数据源配置
     *
     * @param request
     * @param token
     * @return
     */
    @PostMapping("/updateProjectDatasource")
    public BaseResponse updateProjectDatasource(@RequestBody OutlineProjecDatasourceRequest request, @RequestHeader String token) {
        log.info("updateProjectDatasource");
        return outlineProjecDatasourceService.updateProjectDatasource(request, token);
    }

    /**
     * 获取项目管理大纲体系节点数据源配置
     *
     * @param id
     * @return
     */
    @GetMapping("/getProjectDatasourceById")
    public BaseResponse getProjectDatasourceById(@RequestParam("id") String id) {
        log.info("getProjectDatasourceById");
        return outlineProjecDatasourceService.getProjectDatasourceById(id);
    }

    /**
     * 分页查询项目管理大纲体系节点数据源配置
     *
     * @param request
     * @return
     */
    @PostMapping("/getProjectDatasourcePage")
    public BaseResponse getProjectDatasourcePage(@RequestBody OutlineProjecDatasourceRequest request) {
        log.info("getProjectDatasourcePage");
        return outlineProjecDatasourceService.getProjectDatasourcePage(request);
    }

    /**
     * 自动保存项目管理大纲体系节点数据源配置
     *
     * @param request
     * @param token
     * @return
     */
    @PostMapping("/autoSaveProjectDatasource")
    public BaseResponse autoSaveProjectDatasource(@RequestBody OutlineProjecDatasourceRequest request, @RequestHeader String token) {
        log.info("autoSaveProjectDatasource");
        if (ValidateUtil.isEmpty(request.getId())) {
            return outlineProjecDatasourceService.createProjectDatasource(request, token);
        } else {
            return outlineProjecDatasourceService.updateProjectDatasource(request, token);
        }
    }
}
