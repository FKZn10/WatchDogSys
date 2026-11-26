package com.jpmsoft.projectProducer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.ContractMeasurementContractPageRequest;
import com.jpmsoft.common.model.requestAndResponse.Contract.ContractMeasurementValuationModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.contract.IContractMeasurementValuationService;
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
public class ContractMeasurementValuationController {

    @Resource
    private IContractMeasurementValuationService contractMeasurementValuationService;

    @PostMapping("/createOrUpdateContractMeasurementValuation")
    public BaseResponse createOrUpdateContractMeasurementValuation(
            @RequestBody ContractMeasurementValuationModel request,
            @RequestHeader("token") String token) {
        log.info("createOrUpdateContractMeasurementValuation");
        return contractMeasurementValuationService.createOrUpdateContractMeasurementValuation(request, token);
    }

    @GetMapping("/getContractMeasurementValuationById")
    public BaseResponse getContractMeasurementValuationById(
            @RequestParam("id") String id,
            @RequestHeader("token") String token) {
        log.info("getContractMeasurementValuationById");
        return contractMeasurementValuationService.getContractMeasurementValuationById(id, token);
    }

    @TenantId
    @PostMapping("/getContractMeasurementValuationList")
    public BaseResponse getContractMeasurementValuationList(
            @RequestBody ContractMeasurementValuationModel request,
            @RequestHeader("token") String token) {
        log.info("getContractMeasurementValuationList");
        return contractMeasurementValuationService.getContractMeasurementValuationList(request, token);
    }

    @TenantId
    @PostMapping("/getContractMeasurementValuationClassInfo")
    public BaseResponse getContractMeasurementValuationClassInfo(
            @RequestBody ContractMeasurementValuationModel request,
            @RequestHeader("token") String token) {
        log.info("getContractMeasurementValuationClassInfo");
        return contractMeasurementValuationService.getContractMeasurementValuationClassInfo(request, token);
    }

    @PostMapping("/getFinishedConsultAndInternalAgencyContractListByPId")
    public BaseResponse getFinishedConsultAndInternalAgencyContractListByPId(
            @RequestBody ContractMeasurementContractPageRequest request,
            @RequestHeader("token") String token) {
        log.info("getFinishedConsultAndInternalAgencyContractListByPId");
        return contractMeasurementValuationService
                .getFinishedConsultAndInternalAgencyContractListByPId(request, token);
    }
}
