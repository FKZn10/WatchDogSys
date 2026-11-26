package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.projectOutline.OutlineDatasourceTempRequest;
import com.jpmsoft.userProducer.service.IOutlineDatasourceTempService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 业务功能数据源关联
 *
 * @author tianhelin
 * @date 2025/6/26
 */
@Slf4j
@RestController
@Api(value = "项目管理大纲-业务功能数据源关联", tags = "项目管理大纲-业务功能数据源关联")
@RequestMapping("/v1/projectOutline/datasourceTemp")
public class OutlineDatasourceTempController {
    @Resource
    private IOutlineDatasourceTempService outlineDatasourceTempService;

    /**
     * 新增或修改业务功能数据源关联
     *
     * @param request
     * @return
     */
    @ApiOperation("新增或修改业务功能数据源关联")
    @PostMapping("/createOrUpdateDatasourceTemp")
    public BaseResponse createOrUpdateDatasourceTemp(@RequestBody OutlineDatasourceTempRequest request) {
        log.info("createOrUpdateDatasourceTemp");
        return outlineDatasourceTempService.createOrUpdateDatasourceTemp(request);
    }

    /**
     * 删除业务功能数据源关联
     *
     * @param id
     * @return
     */
    @ApiOperation("删除业务功能数据源关联")
    @GetMapping("/deleteDatasourceTempById")
    public BaseResponse deleteDatasourceTempById(@RequestParam("id") String id) {
        log.info("deleteDatasourceTempById");
        return outlineDatasourceTempService.deleteDatasourceTempById(id);
    }

    /**
     * 分页查询业务功能数据源关联
     *
     * @param request
     * @return
     */
    @ApiOperation("分页查询业务功能数据源关联")
    @PostMapping("/getDatasourceTempPage")
    public BaseResponse getDatasourceTempPage(@RequestBody OutlineDatasourceTempRequest request) {
        log.info("getDatasourceTempPage");
        return outlineDatasourceTempService.getDatasourceTempPage(request);
    }

    /**
     * 批量新增或修改业务功能数据源关联
     *
     * @param datasourceTempList
     * @return
     */
    @ApiOperation("批量新增或修改业务功能数据源关联")
    @PostMapping("/batchCreateOrUpdateDatasourceTemp")
    public BaseResponse batchCreateOrUpdateDatasourceTemp(@RequestBody List<OutlineDatasourceTempRequest> datasourceTempList) {
        log.info("batchCreateOrUpdateDatasourceTemp");
        return outlineDatasourceTempService.batchCreateOrUpdateDatasourceTemp(datasourceTempList);
    }
}
