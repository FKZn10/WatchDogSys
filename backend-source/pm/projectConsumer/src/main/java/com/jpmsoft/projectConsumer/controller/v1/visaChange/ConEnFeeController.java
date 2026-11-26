package com.jpmsoft.projectConsumer.controller.v1.visaChange;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.visaChange.ConEnFeeDTO;
import com.jpmsoft.projectConsumer.feign.VisaFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Slf4j
@RestController
@Api(tags = "变更签证-洽商费用申请", value = "变更签证-洽商费用申请")
@RequestMapping("/v1/ConEnFee")
public class ConEnFeeController {
    @Resource
    private VisaFeign visaFeign;

    @ApiOperation(value = "新增或修改洽商费用申请")
    @PostMapping("/createOrUpdateConEnFee")
    public BaseResponse createOrUpdateConEnFee(@RequestBody ConEnFeeDTO request) {
        log.info("createOrUpdateConEnFee");
        return visaFeign.createOrUpdateConEnFee(request);
    }

    @ApiOperation(value = "分页查洽商费用申请")
    @PostMapping("/getConEnFeeList")
    public BaseResponse getConEnFeeList(@RequestBody ConEnFeeDTO request, @RequestHeader String token) {
        log.info("getConEnFeeList");
        return visaFeign.getConEnFeeList(request, token);
    }

    @ApiOperation(value = "ID查洽商费用申请")
    @GetMapping("/getConEnFeeById")
    public BaseResponse getConEnFeeById(@RequestParam String id) {
        log.info("getConEnFeeById");
        return visaFeign.getConEnFeeById(id);
    }
}
