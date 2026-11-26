package com.jpmsoft.projectConsumer.controller.v1.completion;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Completion.CompletionAcceptanceAllRequest;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@Api(tags = "竣工管理-竣工验收（合并版）", value = "竣工管理-竣工验收（合并版）")
@RequestMapping("/v1/acceptanceAll")
public class CompletionAcceptanceAllController {
    @Autowired
    private BaseFeign baseFeign;


    @ApiOperation(value = "新增或修改竣工验收（合并版）")
    @PostMapping("/createOrUpdateCompletionAcceptanceAll")
    public BaseResponse createOrUpdateCompletionAcceptanceAll(@RequestBody CompletionAcceptanceAllRequest request) {
        log.info("createOrUpdateCompletionAcceptanceAll");
        return baseFeign.createOrUpdateCompletionAcceptanceAll(request);
    }

    @ApiOperation(value = "分页查询竣工验收（合并版）")
    @PostMapping("/getCompletionAcceptanceAllList")
    public BaseResponse getCompletionAcceptanceAllList(@RequestBody CompletionAcceptanceAllRequest request, @RequestHeader String token) {
        log.info("getCompletionAcceptanceAllList");
        return baseFeign.getCompletionAcceptanceAllList(request, token);
    }


    @ApiOperation(value = "分页查询竣工验收（合并版）- 不鉴权")
    @PostMapping("/getCompletionAcceptanceAllListNoAuth")
    public BaseResponse getCompletionAcceptanceAllListNoAuth(@RequestBody CompletionAcceptanceAllRequest request) {
        log.info("getCompletionAcceptanceAllListNoAuth");
        return baseFeign.getCompletionAcceptanceAllListNoAuth(request);
    }

    @ApiOperation(value = "Id查询竣工验收（合并版）")
    @GetMapping("/getCompletionAcceptanceAllById")
    public BaseResponse getCompletionAcceptanceAllById(@RequestParam String caaId) {
        log.info("getCompletionAcceptanceAllById");
        return baseFeign.getCompletionAcceptanceAllById(caaId);
    }
}
