package com.jpmsoft.projectConsumer.controller.v1.qualitySafety;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Qs.QsGroundCollapseModel;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "地面坍塌情况", value = "地面坍塌情况")
@RequestMapping("/safety/v1/qsGroundCollapse")
public class QsGroundCollapseController {
    @Autowired
    private BaseFeign baseFeign;

    @ApiOperation(value = "创建或修改地面坍塌情况")
    @PostMapping("/createOrUpdateQsGroundCollapse")
    public BaseResponse createOrUpdateQsGroundCollapse(@RequestBody QsGroundCollapseModel request) {
        log.info("createOrUpdateQsGroundCollapse");
        return baseFeign.createOrUpdateQsGroundCollapse(request);
    }

    @ApiOperation(value = "分页查询地面坍塌情况")
    @PostMapping("/getQsGroundCollapseList")
    public BaseResponse getQsGroundCollapseList(@RequestBody QsGroundCollapseModel request, @RequestHeader String token) {
        log.info("getQsGroundCollapseList");
        return baseFeign.getQsGroundCollapseList(request, token);
    }

    @ApiOperation(value = "ID查询地面坍塌情况")
    @GetMapping("/getQsGroundCollapseById")
    public BaseResponse getQsGroundCollapseById(@RequestParam String gcId) {
        log.info("getQsGroundCollapseById");
        return baseFeign.getQsGroundCollapseById(gcId);
    }
}
