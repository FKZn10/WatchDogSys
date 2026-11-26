package com.jpmsoft.projectConsumer.controller.v1.oa;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.oa.OaAnnualPlanChangeDto;
import com.jpmsoft.projectConsumer.feign.OaFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2022/12/2 16:01
 */
@Slf4j
@RestController
@Api(tags = "年度项目计划变更", value = "年度项目计划变更")
@RequestMapping("/v1/OA/annualPlanChange")
public class OaAnnualPlanChangeController {

    @Resource
    private OaFeign oaFeign;

    @ApiOperation(value = "新增或修改年度项目计划变更")
    @PostMapping("/createOrUpdateOaAnnualPlanChange")
    public BaseResponse createOrUpdateOaAnnualPlanChange(@RequestBody OaAnnualPlanChangeDto request) {
        log.info("createOrUpdateOaAnnualPlanChange");
        return oaFeign.createOrUpdateOaAnnualPlanChange(request);
    }

    @ApiOperation(value = "分页查询年度项目计划变更")
    @PostMapping("/getOaAnnualPlanChangeList")
    public BaseResponse getOaAnnualPlanChangeList(@RequestBody OaAnnualPlanChangeDto request) {
        log.info("getOaAnnualPlanChangeList");
        return oaFeign.getOaAnnualPlanChangeList(request);
    }

    @ApiOperation(value = "ID查询年度项目计划变更")
    @GetMapping("/getOaAnnualPlanChangeById")
    public BaseResponse getOaAnnualPlanChangeById(@RequestParam String id) {
        log.info("getOaAnnualPlanChangeById");
        return oaFeign.getOaAnnualPlanChangeById(id);
    }

    @ApiOperation(value = "审批通过同步年度项目计划变更信息到年度项目计划")
    @GetMapping("/approval2SyncOaAnnualPlan")
    public BaseResponse approval2SyncOaAnnualPlan(@RequestParam String id) {
        log.info("approval2SyncOaAnnualPlan");
        return oaFeign.approval2SyncOaAnnualPlan(id);
    }

}
