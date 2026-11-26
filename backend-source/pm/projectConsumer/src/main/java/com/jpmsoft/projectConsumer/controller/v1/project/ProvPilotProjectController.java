package com.jpmsoft.projectConsumer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.ProvPilotProjectImportRequest;
import com.jpmsoft.common.model.requestAndResponse.Project.ProvPilotProjectModel;
import com.jpmsoft.projectConsumer.feign.ProvPilotProjectFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 省试点项目 Controller（消费端 PC）
 *
 * @author whjp
 * @since 2026-09-16
 */
@Slf4j
@RestController
@Api(tags = "省试点项目", value = "省试点项目")
@RequestMapping("/v1/provPilotProject")
public class ProvPilotProjectController {

    @Resource
    private ProvPilotProjectFeign provPilotProjectFeign;

    @ApiOperation(value = "编辑省试点项目（新增/修改）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "令牌", required = true, paramType = "header", dataType = "string")
    })
    @PostMapping("/createOrUpdate")
    public BaseResponse createOrUpdate(@RequestBody ProvPilotProjectModel request, @RequestHeader String token) {
        return provPilotProjectFeign.createOrUpdate(request, token);
    }

    @ApiOperation(value = "省试点项目列表查询（分页+排序+筛选）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "令牌", required = true, paramType = "header", dataType = "string")
    })
    @PostMapping("/getList")
    public BaseResponse getList(@RequestBody ProvPilotProjectModel request, @RequestHeader String token) {
        return provPilotProjectFeign.getList(request, token);
    }

    @ApiOperation(value = "省试点项目详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "试点项目主键 id", required = true, paramType = "query", dataType = "string")
    })
    @GetMapping("/getById")
    public BaseResponse getById(@RequestParam("id") String id) {
        return provPilotProjectFeign.getById(id);
    }

    @ApiOperation(value = "省试点项目删除（真删除，批量）")
    @PostMapping("/deleteBatch")
    public BaseResponse deleteBatch(@RequestBody List<String> ids) {
        return provPilotProjectFeign.deleteBatch(ids);
    }

    @ApiOperation(value = "省试点项目补充导入")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "令牌", required = true, paramType = "header", dataType = "string")
    })
    @PostMapping("/importProvPilotProject")
    public BaseResponse importProvPilotProject(@RequestBody ProvPilotProjectImportRequest request, @RequestHeader String token) {
        return provPilotProjectFeign.importProvPilotProject(request, token);
    }

    @ApiOperation(value = "省试点项目导出数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "令牌", required = true, paramType = "header", dataType = "string")
    })
    @PostMapping("/getExportList")
    public List<ProvPilotProjectModel> getExportList(@RequestBody ProvPilotProjectModel request, @RequestHeader String token) {
        return provPilotProjectFeign.getExportList(request, token);
    }
}