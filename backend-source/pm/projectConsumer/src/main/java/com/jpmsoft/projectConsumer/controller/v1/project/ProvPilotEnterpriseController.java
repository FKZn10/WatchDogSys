package com.jpmsoft.projectConsumer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.ProvPilotEnterpriseImportRequest;
import com.jpmsoft.common.model.requestAndResponse.Project.ProvPilotEnterpriseModel;
import com.jpmsoft.projectConsumer.feign.ProvPilotEnterpriseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 省试点企业 Controller（消费端 PC）
 *
 * @author whjp
 * @since 2026-09-17
 */
@Slf4j
@RestController
@Api(tags = "省试点企业", value = "省试点企业")
@RequestMapping("/v1/provPilotEnterprise")
public class ProvPilotEnterpriseController {

    @Resource
    private ProvPilotEnterpriseFeign provPilotEnterpriseFeign;

    @ApiOperation(value = "编辑省试点企业（新增/修改）")
    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "令牌", required = true, paramType = "header", dataType = "string")})
    @PostMapping("/createOrUpdate")
    public BaseResponse createOrUpdate(@RequestBody ProvPilotEnterpriseModel request, @RequestHeader String token) {
        return provPilotEnterpriseFeign.createOrUpdate(request, token);
    }

    @ApiOperation(value = "省试点企业列表查询（分页+排序+筛选）")
    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "令牌", required = true, paramType = "header", dataType = "string")})
    @PostMapping("/getList")
    public BaseResponse getList(@RequestBody ProvPilotEnterpriseModel request, @RequestHeader String token) {
        return provPilotEnterpriseFeign.getList(request, token);
    }

    @ApiOperation(value = "省试点企业详情")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "试点企业主键 id", required = true, paramType = "query", dataType = "string")})
    @GetMapping("/getById")
    public BaseResponse getById(@RequestParam("id") String id) {
        return provPilotEnterpriseFeign.getById(id);
    }

    @ApiOperation(value = "省试点企业删除（真删除，批量）")
    @PostMapping("/deleteBatch")
    public BaseResponse deleteBatch(@RequestBody List<String> ids) {
        return provPilotEnterpriseFeign.deleteBatch(ids);
    }

    @ApiOperation(value = "省试点企业补充导入")
    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "令牌", required = true, paramType = "header", dataType = "string")})
    @PostMapping("/importProvPilotEnterprise")
    public BaseResponse importProvPilotEnterprise(@RequestBody ProvPilotEnterpriseImportRequest request, @RequestHeader String token) {
        return provPilotEnterpriseFeign.importProvPilotEnterprise(request, token);
    }

    @ApiOperation(value = "省试点企业导出数据")
    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "令牌", required = true, paramType = "header", dataType = "string")})
    @PostMapping("/getExportList")
    public List<ProvPilotEnterpriseModel> getExportList(@RequestBody ProvPilotEnterpriseModel request, @RequestHeader String token) {
        return provPilotEnterpriseFeign.getExportList(request, token);
    }

    /**
     * 查询全部年度的试点企业卡片，评估及验收尚无数据来源时标记为不可用。
     *
     * @param token 登录令牌，用于取得租户范围
     * @return 企业总数及评估、验收可用性
     */
    @ApiOperation("试点企业统计")
    @PostMapping("/getEnterpriseStatistics")
    public BaseResponse getEnterpriseStatistics(@RequestHeader("token") String token) {
        return provPilotEnterpriseFeign.getEnterpriseStatistics(token);
    }
}