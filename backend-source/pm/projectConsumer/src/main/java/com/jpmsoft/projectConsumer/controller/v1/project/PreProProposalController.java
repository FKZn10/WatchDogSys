package com.jpmsoft.projectConsumer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProProposalModel;
import com.jpmsoft.projectConsumer.feign.PreFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "项目建议书(项目信息源)", value = "项目建议书(项目信息源)")
@RequestMapping("/v1/proProposal")
public class PreProProposalController {
    @Autowired
    private PreFeign preFeign;

    @ApiOperation(value = "创建修改项目建议书(项目信息源)")
    @PostMapping("/createOrUpdatePreProProposal")
    public BaseResponse createOrUpdatePreProProposal(@RequestBody PreProProposalModel request) {
        log.info("createOrUpdatePreProProposal");
        return preFeign.createOrUpdatePreProProposal(request);
    }

    @ApiOperation(value = "分页查询项目建议书(项目信息源)")
    @PostMapping("/getPreProProposalList")
    public BaseResponse getPreProProposalList(@RequestBody PreProProposalModel request, @RequestHeader String token) {
        log.info("getPreProProposalList");
        return preFeign.getPreProProposalList(request, token);
    }

    @ApiOperation(value = "分页查询项目建议书(统计+列表)")
    @PostMapping("/getPreProProposalStatisticsList")
    public BaseResponse getPreProProposalStatisticsList(@RequestBody PreProProposalModel request, @RequestHeader String token) {
        log.info("getPreProProposalStatisticsList");
        return preFeign.getPreProProposalStatisticsList(request, token);
    }

    @ApiOperation(value = "ID查询项目建议书(项目信息源)")
    @GetMapping("/getPreProProposalById")
    public BaseResponse getPreProProposalById(@RequestParam String ppId) {
        log.info("getPreProProposalById");
        return preFeign.getPreProProposalById(ppId);
    }

    @ApiOperation(value = "审批项目建议书(项目信息源)")
    @GetMapping("/approvePreProProposal")
    public BaseResponse approvePreProProposal(@RequestParam String ppId) {
        log.info("approvePreProProposal");
        return preFeign.approvePreProProposal(ppId);
    }
}
