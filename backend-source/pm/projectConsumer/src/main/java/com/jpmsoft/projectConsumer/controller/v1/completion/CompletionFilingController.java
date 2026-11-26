package com.jpmsoft.projectConsumer.controller.v1.completion;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Completion.CompletionFilingRequest;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "竣工管理-竣工备案", value = "竣工管理-竣工备案")
@RequestMapping("/completion/v1/filing")
public class CompletionFilingController {
    @Autowired
    private BaseFeign baseFeign;

    @ApiOperation(value = "新增或修改竣工备案")
    @PostMapping("/createOrUpdateCompletionFiling")
    public BaseResponse createOrUpdateCompletionFiling(@RequestBody CompletionFilingRequest request) {
        log.info("createOrUpdateCompletionFiling");
        return baseFeign.createOrUpdateCompletionFiling(request);
    }

    @ApiOperation(value = "分页查询竣工备案")
    @PostMapping("/getCompletionFilingList")
    public BaseResponse getCompletionFilingList(@RequestBody CompletionFilingRequest request, @RequestHeader String token) {
        log.info("getCompletionFilingList");
        return baseFeign.getCompletionFilingList(request, token);
    }

    @ApiOperation(value = "分页查询竣工备案-不鉴权")
    @PostMapping("/getCompletionFilingListNoAuth")
    public BaseResponse getCompletionFilingListNoAuth(@RequestBody CompletionFilingRequest request) {
        log.info("getCompletionFilingListNoAuth");
        return baseFeign.getCompletionFilingListNoAuth(request);
    }


    @ApiOperation(value = "ID查询竣工备案")
    @GetMapping("/getCompletionFilingById")
    public BaseResponse getCompletionFilingById(@RequestParam String cfId) {
        log.info("getCompletionFilingById");
        return baseFeign.getCompletionFilingById(cfId);
    }
}
