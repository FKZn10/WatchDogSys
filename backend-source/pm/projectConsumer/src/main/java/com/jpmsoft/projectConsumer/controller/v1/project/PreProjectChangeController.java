package com.jpmsoft.projectConsumer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProjectChangeModel;
import com.jpmsoft.projectConsumer.feign.PreFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "项目信息变更", value = "项目信息变更")
@RequestMapping("/v1/projectChange")
public class PreProjectChangeController {
    @Autowired
    private PreFeign preFeign;

    @ApiOperation(value = "创建修改项目信息变更")
    @PostMapping("/createOrUpdatePreProjectChange")
    public BaseResponse createOrUpdatePreProjectChange(@RequestBody PreProjectChangeModel request, @RequestHeader String token) {
        log.info("createOrUpdatePreProjectChange");
        return preFeign.createOrUpdatePreProjectChange(request, token);
    }

    @ApiOperation(value = "分页项目信息变更")
    @PostMapping("/getPreProjectChangeList")
    public BaseResponse getPreProjectChangeList(@RequestBody PreProjectChangeModel request, @RequestHeader String token) {
        log.info("getPreProjectChangeList");
        return preFeign.getPreProjectChangeList(request, token);
    }

    @ApiOperation(value = "ID查询项目信息变更")
    @GetMapping("/getPreProjectChangeById")
    public BaseResponse getPreProjectChangeById(@RequestParam String pcId) {
        log.info("getPreProjectChangeById");
        return preFeign.getPreProjectChangeById(pcId);
    }

    @ApiOperation(value = "审批修改项目信息")
    @GetMapping("/approvalSyncProInfo")
    public BaseResponse approvalSyncProInfo(@RequestParam String pcId, @RequestHeader String token) {
        log.info("approvalSyncProInfo");
        return preFeign.approvalSyncProInfo(pcId, token);
    }
}
