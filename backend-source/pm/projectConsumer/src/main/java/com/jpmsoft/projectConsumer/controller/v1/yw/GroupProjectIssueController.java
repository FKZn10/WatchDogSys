package com.jpmsoft.projectConsumer.controller.v1.yw;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.yw.GroupProjectIssueModel;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/yw/groupProjectIssue")
@Slf4j
public class GroupProjectIssueController {
    @Autowired
    private BaseFeign baseFeign;

    @PostMapping("/saveOrUpdateGroupProjectIssue")
    public BaseResponse saveOrUpdateGroupProjectIssue(@RequestBody GroupProjectIssueModel request) {
        log.info("saveOrUpdateGroupProjectIssue");
        return baseFeign.saveOrUpdateGroupProjectIssue(request);
    }
    @PostMapping("/getGroupProjectIssueList")
    public BaseResponse getGroupProjectIssueList(@RequestBody GroupProjectIssueModel request, @RequestHeader String token) {
        log.info("getGroupProjectIssueList");
        return baseFeign.getGroupProjectIssueList(request,token);
    }
    @GetMapping("/getGroupProjectIssueDetail")
    public BaseResponse getGroupProjectIssueDetail(@RequestParam String id) {
        log.info("getGroupProjectIssueDetail");
        return baseFeign.getGroupProjectIssueDetail(id);
    }
    @PostMapping("/approvalData")
    public BaseResponse approvalData(@RequestBody GroupProjectIssueModel request) {
        log.info("approvalData");
        return baseFeign.approvalGroupProjectIssuelData(request);
    }
}