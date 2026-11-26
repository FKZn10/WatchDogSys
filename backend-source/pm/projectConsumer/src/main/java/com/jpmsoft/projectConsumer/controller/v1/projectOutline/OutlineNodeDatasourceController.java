package com.jpmsoft.projectConsumer.controller.v1.projectOutline;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.projectOutline.OutlineNodeDatasourceRequest;
import com.jpmsoft.projectConsumer.feign.OutlineFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@Api(value = "项目管理大纲体系模板-节点数据源", tags = "项目管理大纲体系模板-节点数据源")
@RequestMapping("/v1/projectOutline/nodeDatasource")
public class OutlineNodeDatasourceController {
    @Resource
    private OutlineFeign outlineFeign;

    @ApiOperation("新增体系模板-节点数据源")
    @PostMapping("/createOutlineNodeDatasource")
    public BaseResponse createOutlineNodeDatasource(@RequestBody OutlineNodeDatasourceRequest request, @RequestHeader String token) {
        log.info("createOutlineNodeDatasource");
        return outlineFeign.createOutlineNodeDatasource(request, token);
    }

    @ApiOperation("分页查询体系模板-节点数据源")
    @PostMapping("/getOutlineNodeDatasourceList")
    public BaseResponse getOutlineNodeDatasourceList(@RequestBody OutlineNodeDatasourceRequest request) {
        log.info("getOutlineNodeDatasourceList");
        return outlineFeign.getOutlineNodeDatasourceList(request);
    }

    @ApiOperation("ID删除体系模板-节点数据源")
    @GetMapping("/deleteOutlineNodeDatasourceById")
    public BaseResponse deleteOutlineNodeDatasourceById(@RequestParam String id, @RequestHeader String token) {
        log.info("deleteOutlineNodeDatasourceById");
        return outlineFeign.deleteOutlineNodeDatasourceById(id, token);
    }

    @ApiOperation("更新体系模板-节点数据源")
    @PostMapping("/updateOutlineNodeDatasource")
    public BaseResponse updateOutlineNodeDatasource(@RequestBody OutlineNodeDatasourceRequest request, @RequestHeader String token) {
        log.info("updateOutlineNodeDatasource");
        return outlineFeign.updateOutlineNodeDatasource(request, token);
    }

    @ApiOperation("自动保存体系模板-节点数据源")
    @PostMapping("/autoSaveOutlineNodeDatasource")
    public BaseResponse autoSaveOutlineNodeDatasource(@RequestBody OutlineNodeDatasourceRequest request, @RequestHeader String token) {
        log.info("autoSaveOutlineNodeDatasource");
        return outlineFeign.autoSaveOutlineNodeDatasource(request, token);
    }
}
