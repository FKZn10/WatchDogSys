package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecAnswerQuestionRequest;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "答疑补遗", value = "答疑补遗")
@RequestMapping("/v1/answerQuestion")
public class SecAnswerQuestionController {
    @Autowired
    private SecFeign secFeign;

    @ApiOperation(value = "创建或修改答疑补遗")
    @PostMapping("/createOrUpdateSecAnswerQuestion")
    public BaseResponse createOrUpdateSecAnswerQuestion(@RequestBody SecAnswerQuestionRequest request) {
        log.info("createOrUpdateSecAnswerQuestion");
        return secFeign.createOrUpdateSecAnswerQuestion(request);
    }

    @ApiOperation(value = "ID删除答疑补遗")
    @GetMapping("/deleteSecAnswerQuestionByaqId")
    public BaseResponse deleteSecAnswerQuestionByaqId(@RequestParam String aqId) {
        log.info("deleteSecAnswerQuestionByaqId");
        return secFeign.deleteSecAnswerQuestionByaqId(aqId);
    }

    @ApiOperation(value = "ID查询答疑补遗")
    @GetMapping("/getSecAnswerQuestionByaqId")
    public BaseResponse getSecAnswerQuestionByaqId(@RequestParam String aqId) {
        log.info("getSecAnswerQuestionByaqId");
        return secFeign.getSecAnswerQuestionByaqId(aqId);
    }

    @ApiOperation(value = "分页查询答疑补遗 - 多条件筛选")
    @PostMapping("/getSecAnswerQuestionListClassInfo")
    public BaseResponse getSecAnswerQuestionListClassInfo(@RequestBody SecAnswerQuestionRequest request, @RequestHeader String token) {
        log.info("getSecAnswerQuestionListClassInfo");
        return secFeign.getSecAnswerQuestionListClassInfo(request,token);
    }

    @ApiOperation(value = "分页查询答疑补遗 - 多条件筛选")
    @PostMapping("/getSecAnswerQuestionListClass")
    public BaseResponse getSecAnswerQuestionListClass(@RequestBody SecAnswerQuestionRequest request,@RequestHeader String token) {
        log.info("getSecAnswerQuestionListClass");
        return secFeign.getSecAnswerQuestionListClass(request,token);
    }

    @ApiOperation(value = "分页查询答疑补遗")
    @PostMapping("/getSecAnswerQuestionList")
    public BaseResponse getSecAnswerQuestionList(@RequestBody SecAnswerQuestionRequest request, @RequestHeader String token) {
        log.info("getSecAnswerQuestionList");
        return secFeign.getSecAnswerQuestionList(request, token);
    }

    @ApiOperation(value = "分页查询答疑补遗(默认项目标段一对多)")
    @PostMapping("/getSecAnswerQuestionListOne2Many")
    public BaseResponse getSecAnswerQuestionListOne2Many(@RequestBody SecAnswerQuestionRequest request, @RequestHeader String token) {
        log.info("getSecAnswerQuestionListOne2Many");
        return secFeign.getSecAnswerQuestionListOne2Many(request, token);
    }

    @ApiOperation(value = "审批同步招标文件开标时间地点")
    @GetMapping("/approveSyncDocumentBidTimeAndLocation")
    public BaseResponse approveSyncDocumentBidTimeAndLocation(@RequestParam String aqId) {
        log.info("approveSyncDocumentBidTimeAndLocation");
        return secFeign.approveSyncDocumentBidTimeAndLocation(aqId);
    }
}
