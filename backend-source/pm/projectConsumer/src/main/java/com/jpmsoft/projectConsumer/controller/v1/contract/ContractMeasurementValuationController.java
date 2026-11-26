package com.jpmsoft.projectConsumer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.ContractMeasurementContractPageRequest;
import com.jpmsoft.common.model.requestAndResponse.Contract.ContractMeasurementValuationModel;
import com.jpmsoft.projectConsumer.feign.ConFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 合同计量计价低代码自定义数据源
 */
@Slf4j
@RestController
@RequestMapping("/v1/contractMeasurementValuation")
@Api(tags = "合同计量计价低代码自定义数据源")
public class ContractMeasurementValuationController {

    @Resource
    private ConFeign conFeign;

    @ApiOperation("新增或修改合同计量计价")
    @PostMapping("/createOrUpdateContractMeasurementValuation")
    public BaseResponse createOrUpdateContractMeasurementValuation(
            @RequestBody ContractMeasurementValuationModel request,
            @RequestHeader("token") String token) {
        log.info("createOrUpdateContractMeasurementValuation");
        return conFeign.createOrUpdateContractMeasurementValuation(request, token);
    }

    @ApiOperation("获取合同计量计价详情")
    @GetMapping("/getContractMeasurementValuationById")
    public BaseResponse getContractMeasurementValuationById(
            @RequestParam("id") String id,
            @RequestHeader("token") String token) {
        log.info("getContractMeasurementValuationById");
        return conFeign.getContractMeasurementValuationById(id, token);
    }

    @ApiOperation("分页获取合同计量计价列表")
    @PostMapping("/getContractMeasurementValuationList")
    public BaseResponse getContractMeasurementValuationList(
            @RequestBody ContractMeasurementValuationModel request,
            @RequestHeader("token") String token) {
        log.info("getContractMeasurementValuationList");
        return conFeign.getContractMeasurementValuationList(request, token);
    }

    @ApiOperation("获取合同计量计价高级筛选项")
    @PostMapping("/getContractMeasurementValuationClassInfo")
    public BaseResponse getContractMeasurementValuationClassInfo(
            @RequestBody ContractMeasurementValuationModel request,
            @RequestHeader("token") String token) {
        log.info("getContractMeasurementValuationClassInfo");
        return conFeign.getContractMeasurementValuationClassInfo(request, token);
    }

    @ApiOperation("根据项目ID获取已结束的全过程咨询和内部代建合同列表")
    @PostMapping("/getFinishedConsultAndInternalAgencyContractListByPId")
    public BaseResponse getFinishedConsultAndInternalAgencyContractListByPId(
            @RequestBody ContractMeasurementContractPageRequest request,
            @RequestHeader("token") String token) {
        log.info("getFinishedConsultAndInternalAgencyContractListByPId");
        return conFeign.getFinishedConsultAndInternalAgencyContractListByPId(request, token);
    }
}
