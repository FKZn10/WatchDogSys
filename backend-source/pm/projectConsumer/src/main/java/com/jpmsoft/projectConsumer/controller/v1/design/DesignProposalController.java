package com.jpmsoft.projectConsumer.controller.v1.design;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignProposalModel;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "项目建议书", value = "项目建议书")
@RequestMapping("/design/v1/designProposal")
public class DesignProposalController {
    @Autowired
    private BaseFeign baseFeign;

    @ApiOperation(value = "新增或修改项目建议书")
    @PostMapping("/createOrUpdateDesignProposal")
    public BaseResponse createOrUpdateDesignProposal(@RequestBody DesignProposalModel request) {
        log.info("getDesignProposalList");
        return baseFeign.createOrUpdateDesignProposal(request);
    }

    @ApiOperation(value = "分页查询项目建议书")
    @PostMapping("/getDesignProposalList")
    public BaseResponse getDesignProposalList(@RequestBody DesignProposalModel request, @RequestHeader String token) {
        log.info("getDesignProposalList");
        return baseFeign.getDesignProposalList(request, token);
    }

    @ApiOperation(value = "分页查询项目建议书-不鉴权")
    @PostMapping("/getDesignProposalListNoAuth")
    public BaseResponse getDesignProposalListNoAuth(@RequestBody DesignProposalModel request) {
        log.info("getDesignProposalListNoAuth");
        return baseFeign.getDesignProposalListNoAuth(request);
    }

    @ApiOperation(value = "ID查询项目建议书")
    @GetMapping("/getDesignProposalById")
    public BaseResponse getDesignProposalById(@RequestParam String dpId) {
        log.info("getDesignProposalById");
        return baseFeign.getDesignProposalById(dpId);
    }
}
