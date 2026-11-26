package com.jpmsoft.projectConsumer.controller.v1.oa;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Oa.OaWorkorderRequest;
import com.jpmsoft.projectConsumer.feign.OaFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "工单", value = "工单")
@RequestMapping("/OA/WorkOrder")
public class OaWorkorderController {
    @Autowired
    private OaFeign oaFeign;

    @ApiOperation(value = "新增或修改工单")
    @PostMapping("/createOrUpdateOaWorkorder")
    public BaseResponse createOrUpdateOaWorkorder(@RequestBody OaWorkorderRequest request) {
        log.info("createOrUpdateOaWorkorder");
        return oaFeign.createOrUpdateOaWorkorder(request);
    }

    @ApiOperation(value = "分页查询工单")
    @PostMapping("/getOaWorkorderList")
    public BaseResponse getOaWorkorderList(@RequestBody OaWorkorderRequest request, @RequestHeader String token) {
        log.info("getOaWorkorderList");
        return oaFeign.getOaWorkorderList(request, token);
    }

    @ApiOperation(value = "ID查询工单")
    @GetMapping("/getOaWorkorderById")
    public BaseResponse getOaWorkorderById(@RequestParam String woId) {
        log.info("getOaWorkorderById");
        return oaFeign.getOaWorkorderById(woId);
    }

    @ApiOperation(value = "更新工单问题解决回复信息")
    @PostMapping("/updateReplyInfo")
    public BaseResponse updateReplyInfo(@RequestBody OaWorkorderRequest request) {
        log.info("updateReplyInfo");
        return oaFeign.updateReplyInfo(request);
    }
}
