package com.jpmsoft.projectConsumer.controller.v1.oa;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.oa.OaAnnualPlanDto;
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
@Api(tags = "年度项目计划", value = "年度项目计划")
@RequestMapping("/v1/OA/annualPlan")
public class OaAnnualPlanController {

    @Resource
    private OaFeign oaFeign;

    @ApiOperation(value = "新增或修改年度项目计划")
    @PostMapping("/createOrUpdateOaAnnualPlan")
    public BaseResponse createOrUpdateOaAnnualPlan(@RequestBody OaAnnualPlanDto request) {
        log.info("createOrUpdateOaAnnualPlan");
        return oaFeign.createOrUpdateOaAnnualPlan(request);
    }


    @ApiOperation(value = "分页查询年度项目计划")
    @PostMapping("/getOaAnnualPlanList")
    public BaseResponse getOaAnnualPlanList(@RequestBody OaAnnualPlanDto request) {
        log.info("getOaAnnualPlanList");
        return oaFeign.getOaAnnualPlanList(request);
    }

    @ApiOperation(value = "ID查询年度项目计划")
    @GetMapping("/getOaAnnualPlanById")
    public BaseResponse getOaAnnualPlanById(@RequestParam String id) {
        log.info("getOaAnnualPlanById");
        return oaFeign.getOaAnnualPlanById(id);
    }


    @ApiOperation(value = "分页查询年度项目计划清单信息")
    @PostMapping("/getOaAnnualPlanDtlList")
    public BaseResponse getOaAnnualPlanDtlList(@RequestBody OaAnnualPlanDto request) {
        log.info("getOaAnnualPlanDtlList");
        return oaFeign.getOaAnnualPlanDtlList(request);
    }

}
