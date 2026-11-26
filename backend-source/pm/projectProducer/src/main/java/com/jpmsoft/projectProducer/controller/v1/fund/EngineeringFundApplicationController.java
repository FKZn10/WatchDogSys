package com.jpmsoft.projectProducer.controller.v1.fund;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.EngineeringFundApplicationRejectRequest;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectProducer.pojo.dto.fund.EngineeringFundApplicationDto;
import com.jpmsoft.projectProducer.pojo.request.fund.EngineeringFundApplicationRequest;
import com.jpmsoft.projectProducer.pojo.response.EFAResponse;
import com.jpmsoft.projectProducer.service.fund.IEngineeringFundApplicationService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/engineeringFundApplication")
public class EngineeringFundApplicationController {
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private IEngineeringFundApplicationService engineeringFundApplicationService;
    @PostMapping("/createOrUpdateEngineeringFundApplication")
    public BaseResponse createOrUpdateEngineeringFundApplication(@RequestBody EngineeringFundApplicationDto dto) {
        log.info("createOrUpdateEngineeringFundApplication:{}",JacksonUtils.toJSONString(dto));
        return engineeringFundApplicationService.createOrUpdateEngineeringFundApplication(dto);
    }
    @PostMapping("/approvalData")
    public BaseResponse approvalData(@RequestBody EngineeringFundApplicationRequest request) {
        return engineeringFundApplicationService.approvalData(request);
    }
    @PostMapping("/getEngineeringFundApplicationList")
    public BaseResponse getEngineeringFundApplicationList(@RequestBody EngineeringFundApplicationRequest request,@RequestHeader String token) {
        return engineeringFundApplicationService.getEngineeringFundApplicationList(request,redisUtils.getUid(token));
    }
    @PostMapping("/getEngineeringFundApplicationById")
    public BaseResponse getEngineeringFundApplicationById(@RequestBody EngineeringFundApplicationRequest request) {
        return engineeringFundApplicationService.getEngineeringFundApplicationById(request.getId());
    }

    /**
     * 获取资金文号信息
     * @param request
     * @return
     */
    @PostMapping("/getFundNumberInfo")
    public BaseResponse getFundNumberInfo(@RequestBody EngineeringFundApplicationRequest request) {
        log.info("getFundNumberInfo");
        log.info("getFundNumberInfo request:{}",JacksonUtils.toJSONString(request));
        return engineeringFundApplicationService.getFundNumberInfo(request);
    }

    /**
     * 获取银行账户信息
     * @param request
     * @return
     */
    @PostMapping("/getBankInfo")
    public BaseResponse getBankInfo(@RequestBody EngineeringFundApplicationRequest request) {
        log.info("getBankInfo");
        log.info("getBankInfo request:{}",JacksonUtils.toJSONString(request));
        return engineeringFundApplicationService.getBankInfo(request);
    }
    /****
     * 发送业务数据
     */
    @PostMapping("/sendData")
    public BaseResponse sendData(@RequestBody EngineeringFundApplicationRequest request) {
        log.info("sendData request:{}",JacksonUtils.toJSONString(request));
        return engineeringFundApplicationService.sendData(request);
    }
    /****
     * 接收审批结果、支付结果等回调数据
     */
    @PostMapping("/receiveCallback")
    public BaseResponse receiveCallback(@RequestBody EFAResponse response) {
        log.info("receiveCallback request:{}",JacksonUtils.toJSONString(response));
        return engineeringFundApplicationService.receiveCallback(response);
    }

    /**
     * 驳回流程到发起人
     * @param engineeringFundApplicationRejectRequest
     * @return
     */
//    @PostMapping("/reject")
    public BaseResponse reject(@RequestBody EngineeringFundApplicationRejectRequest engineeringFundApplicationRejectRequest){
        return engineeringFundApplicationService.reject(engineeringFundApplicationRejectRequest);
    }

}
