package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecInquiryTeamModel;
import com.jpmsoft.projectProducer.service.secInfo.ISecInquiryTeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Slf4j
@RestController
@RequestMapping("/v1/inquiryTeam")
public class SecInquiryTeamController {

    @Resource
    private ISecInquiryTeamService secInquiryTeamService;


    @PostMapping("/createOrUpdateSecInquiryTeam")
    public BaseResponse createOrUpdateSecInquiryTeam(@RequestBody SecInquiryTeamModel request) {
        log.info("createOrUpdateSecInquiryTeam");
        return secInquiryTeamService.createOrUpdateSecInquiryTeam(request);
    }

    @GetMapping("/deleteSecInquiryTeamBySitId")
    public BaseResponse deleteSecInquiryTeamBySitId(@RequestParam String sitId) {
        log.info("deleteSecInquiryTeamBySitId");
        return secInquiryTeamService.deleteSecInquiryTeamBySitId(sitId);
    }

    @GetMapping("/getSecInquiryTeamBySitId")
    public BaseResponse getSecInquiryTeamBySitId(@RequestParam String sitId) {
        log.info("getSecInquiryTeamBySitId");
        return secInquiryTeamService.getSecInquiryTeamBySitId(sitId);
    }
}
