package com.jpmsoft.projectProducer.controller.v1.yw;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.yw.GroupProjectIssueModel;
import com.jpmsoft.projectProducer.service.leaderBoard.GroupProjectIssueService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/yw/groupProjectIssue")
@Slf4j
public class GroupProjectIssueController {
    @Autowired
    private GroupProjectIssueService groupProjectIssueService;
    @Autowired
    private RedisUtils redisUtils;
    @PostMapping("/saveOrUpdateGroupProjectIssue")
    public BaseResponse saveOrUpdateGroupProjectIssue(@RequestBody GroupProjectIssueModel request) {
        log.info("saveOrUpdateGroupProjectIssue");
        return groupProjectIssueService.saveOrUpdateGroupProjectIssue(request);
    }
    @PostMapping("/getGroupProjectIssueList")
    public BaseResponse getGroupProjectIssueList(@RequestBody GroupProjectIssueModel request, @RequestHeader String token) {
        log.info("getGroupProjectIssueList");
        return groupProjectIssueService.getGroupProjectIssueList(request,token);
    }
    @GetMapping("/getGroupProjectIssueDetail")
    public BaseResponse getGroupProjectIssueDetail(@RequestParam String id) {
        log.info("getGroupProjectIssueDetail");
        return groupProjectIssueService.getGroupProjectIssueDetail(id);
    }
    @PostMapping("/approvalData")
    public BaseResponse approvalData(@RequestBody GroupProjectIssueModel request) {
        log.info("approvalData");
        return groupProjectIssueService.approvalData(request);
    }
}