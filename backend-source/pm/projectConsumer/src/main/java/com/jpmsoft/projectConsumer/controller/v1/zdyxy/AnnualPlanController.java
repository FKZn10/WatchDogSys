package com.jpmsoft.projectConsumer.controller.v1.zdyxy;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.zdyxy.AnnualPlanModel;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectConsumer.feign.AnnualPlanFeign;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/annualPlan")
@Slf4j
public class AnnualPlanController {
    @Autowired
    private AnnualPlanFeign annualPlanFeign;

    @ApiOperation(value = "新增或修改记录")
    @PostMapping("/saveOrUpdateData")
    public BaseResponse saveOrUpdateData(@RequestBody AnnualPlanModel request) {
        log.info("saveOrUpdateData:{}", JacksonUtils.toJSONString(request));
        return annualPlanFeign.saveOrUpdateData(request);
    }
    @PostMapping("/approvalData")
    public BaseResponse approvalData(@RequestBody AnnualPlanModel request) {
        log.info("approvalData:{}", JacksonUtils.toJSONString(request));
        return annualPlanFeign.approvalData(request);
    }
    @ApiOperation(value = "分页查询记录")
    @PostMapping("/getList")
    public BaseResponse getList(@RequestBody AnnualPlanModel request, @RequestHeader String token) {
        log.info("getList:{}", JacksonUtils.toJSONString(request));
        return annualPlanFeign.getList(request, token);
    }
    @PostMapping("/getByYear")
    public BaseResponse getByYear(@RequestBody AnnualPlanModel request) {
        log.info("getByYear:{}", JacksonUtils.toJSONString(request));
        return annualPlanFeign.getByYear(request);
    }
    @PostMapping("/getInfo")
    public BaseResponse getInfo(@RequestBody AnnualPlanModel request) {
        log.info("getInfo:{}", JacksonUtils.toJSONString(request));
        return annualPlanFeign.getInfo(request);
    }
    @ApiOperation(value = "ID查询记录")
    @GetMapping("/getDetail")
    public BaseResponse getDetail(@RequestParam("id") String id) {
        log.info("getDetail");
        return annualPlanFeign.getDetail(id);
    }
}