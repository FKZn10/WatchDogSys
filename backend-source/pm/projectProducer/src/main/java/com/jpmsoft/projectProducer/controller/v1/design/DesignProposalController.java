package com.jpmsoft.projectProducer.controller.v1.design;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignProposalModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.design.IDesignProposalService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/designProposal")
public class DesignProposalController {

    @Resource
    private IDesignProposalService designProposalService;
    @Resource
    private RedisUtils redisUtils;


    @TenantId
    @PostMapping("/createOrUpdateDesignProposal")
    public BaseResponse createOrUpdateDesignProposal(@RequestBody DesignProposalModel request) {
        log.info("getDesignProposalList");
        return designProposalService.createOrUpdateDesignProposal(request);
    }

    @TenantId
    @PostMapping("/getDesignProposalList")
    public BaseResponse getDesignProposalList(@RequestBody DesignProposalModel request, @RequestHeader String token) {
        log.info("getDesignProposalList");
        return designProposalService.getDesignProposalList(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getDesignProposalListNoAuth")
    public BaseResponse getDesignProposalListNoAuth(@RequestBody DesignProposalModel request) {
        log.info("getDesignProposalListNoAuth");
        return designProposalService.getDesignProposalListNoAuth(request);
    }

    @GetMapping("/getDesignProposalById")
    public BaseResponse getDesignProposalById(@RequestParam String dpId) {
        log.info("getDesignProposalById");
        return designProposalService.getDesignProposalById(dpId);
    }
}
