package com.jpmsoft.projectConsumer.controller.v1.fund;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.EngineeringFundApplicationRejectRequest;
import com.jpmsoft.projectConsumer.pojo.dto.fund.EngineeringFundApplicationDto;
import com.jpmsoft.projectConsumer.feign.FundFeign;
import com.jpmsoft.projectConsumer.request.fund.EngineeringFundApplicationRequest;
import com.jpmsoft.projectConsumer.response.EFAResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/engineeringFundApplication")
public class EngineeringFundApplicationController {
    @Autowired
    private FundFeign fundFeign;
    @PostMapping("/createOrUpdateEngineeringFundApplication")
    public BaseResponse createOrUpdateEngineeringFundApplication(@RequestBody EngineeringFundApplicationDto dto) {
        log.info("createOrUpdateEngineeringFundApplication");
        return fundFeign.createOrUpdateEngineeringFundApplication(dto);
    }
    @PostMapping("/getEngineeringFundApplicationList")
    public BaseResponse getEngineeringFundApplicationList(@RequestBody EngineeringFundApplicationRequest request,@RequestHeader String token) {
        log.info("getEngineeringFundApplicationList");
        return fundFeign.getEngineeringFundApplicationList(request,token);
    }
    @PostMapping("/getEngineeringFundApplicationById")
    public BaseResponse getEngineeringFundApplicationById(@RequestBody EngineeringFundApplicationRequest request) {
        log.info("getEngineeringFundApplicationById");
        return fundFeign.getEngineeringFundApplicationById(request);
    }
    @PostMapping("/approvalData")
    public BaseResponse approvalData(@RequestBody EngineeringFundApplicationRequest request) {
        log.info("approvalData");
        return fundFeign.approvalData(request);
    }
    @PostMapping("/getFundNumberInfo")
    public BaseResponse getFundNumberInfo(@RequestBody EngineeringFundApplicationRequest request) {
        log.info("getFundNumberInfo");
        return fundFeign.getFundNumberInfo(request);
    }
    @PostMapping("/getBankInfo")
    public BaseResponse getBankInfo(@RequestBody EngineeringFundApplicationRequest request) {
        log.info("getBankInfo");
        return fundFeign.getBankInfo(request);
    }


    /****
     * 发送业务数据
     */
    @PostMapping("/sendData")
    public BaseResponse sendData(@RequestBody EngineeringFundApplicationRequest request) {
        log.info("sendData");
        return fundFeign.sendData(request);
    }
    /****
     * 接收审批结果、支付结果等回调数据
     */
    @PostMapping("/receiveCallback")
    public BaseResponse receiveCallback(@RequestBody EFAResponse response) {
        log.info("receiveCallback");
        return fundFeign.receiveCallback(response);
    }

    /**
     * 驳回流程到发起人
     * @param engineeringFundApplicationRejectRequest
     * @return
     */
    @PostMapping("/reject")
    public BaseResponse reject(@RequestBody EngineeringFundApplicationRejectRequest engineeringFundApplicationRejectRequest){
        return fundFeign.reject(engineeringFundApplicationRejectRequest);
    }
    
}
