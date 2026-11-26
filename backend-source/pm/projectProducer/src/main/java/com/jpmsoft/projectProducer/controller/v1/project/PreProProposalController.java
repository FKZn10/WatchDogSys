package com.jpmsoft.projectProducer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProProposalModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.project.IPreProProposalService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/proProposal")
public class PreProProposalController {

    @Resource
    private IPreProProposalService preProProposalService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdatePreProProposal")
    public BaseResponse createOrUpdatePreProProposal(@RequestBody PreProProposalModel request) {
        log.info("createOrUpdatePreProProposal");
        return preProProposalService.createOrUpdatePreProProposal(request);
    }

    @TenantId
    @PostMapping("/getPreProProposalList")
    public BaseResponse getPreProProposalList(@RequestBody PreProProposalModel request, @RequestHeader String token) {
        log.info("getPreProProposalList");
        return preProProposalService.getPreProProposalList(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getPreProProposalStatisticsList")
    public BaseResponse getPreProProposalStatisticsList(@RequestBody PreProProposalModel request, @RequestHeader String token) {
        log.info("getPreProProposalStatisticsList");
        return preProProposalService.getPreProProposalStatisticsList(request, redisUtils.getUid(token));
    }


    @GetMapping("/getPreProProposalById")
    public BaseResponse getPreProProposalById(@RequestParam String ppId) {
        log.info("getPreProProposalById");
        return preProProposalService.getPreProProposalById(ppId);
    }

    @GetMapping("/approvePreProProposal")
    public BaseResponse approvePreProProposal(@RequestParam String ppId) {
        log.info("approvePreProProposal");
        return preProProposalService.approvePreProProposal(ppId);
    }
}
