package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecInquiryTeamModel;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@Api(tags = "询价小组", value = "询价小组")
@RequestMapping("/v1/inquiryTeam")
public class SecInquiryTeamController {
    @Autowired
    private SecFeign secFeign;

    @ApiOperation(value = "创建或修改询价小组")
    @PostMapping("/createOrUpdateSecInquiryTeam")
    public BaseResponse createOrUpdateSecInquiryTeam(@RequestBody SecInquiryTeamModel request) {
        log.info("createOrUpdateSecInquiryTeam");
        return secFeign.createOrUpdateSecInquiryTeam(request);
    }

    @ApiOperation(value = "ID删除询价小组")
    @GetMapping("/deleteSecInquiryTeamBySitId")
    public BaseResponse deleteSecInquiryTeamBySitId(@RequestParam String sitId) {
        log.info("deleteSecInquiryTeamBySitId");
        return secFeign.deleteSecInquiryTeamBySitId(sitId);
    }

    @ApiOperation(value = "ID查询询价小组")
    @GetMapping("/getSecInquiryTeamBySitId")
    public BaseResponse getSecInquiryTeamBySitId(@RequestParam String sitId) {
        log.info("getSecInquiryTeamBySitId");
        return secFeign.getSecInquiryTeamBySitId(sitId);
    }
}
