package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecExpertDeleteModel;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import com.jpmsoft.projectConsumer.request.ReturnInitiatorDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 专家删除申请
 */
@Slf4j
@RestController
@Api(tags = "专家删除申请", value = "专家删除申请")
@RequestMapping("/v1/secExpertDelete")
public class SecExpertDeleteController {
    @Resource
    private SecFeign secFeign;

    @ApiOperation(value = "新增或修改专家删除申请")
    @PostMapping("/createOrUpdateSecExpertDelete")
    public BaseResponse createOrUpdateSecExpertDelete(@RequestBody SecExpertDeleteModel request, @RequestHeader String token) {
        log.info("createOrUpdateSecExpertDelete");
        return secFeign.createOrUpdateSecExpertDelete(request, token);
    }

    @ApiOperation(value = "获取专家删除申请列表")
    @PostMapping("/getSecExpertDeleteList")
    public BaseResponse getSecExpertDeleteList(@RequestBody SecExpertDeleteModel request) {
        log.info("getSecExpertDeleteList");
        return secFeign.getSecExpertDeleteList(request);
    }

    @ApiOperation(value = "根据id获取专家删除申请详情")
    @GetMapping("/getSecExpertDeleteById")
    public BaseResponse getSecExpertDeleteById(@RequestParam String id) {
        log.info("getSecExpertDeleteById");
        return secFeign.getSecExpertDeleteById(id);
    }

    @ApiOperation(value = "同步专家删除申请数据")
    @GetMapping("/syncSecExpertDeleteData")
    public BaseResponse syncSecExpertDeleteData(@RequestParam String id) {
        log.info("syncSecExpertDeleteData");
        return secFeign.syncSecExpertDeleteData(id);
    }

    @ApiOperation("退回删除申请到发起人")
    @PostMapping("/returnInitiatorToTask")
    public BaseResponse returnInitiatorToTask(@RequestBody ReturnInitiatorDto request, @RequestHeader String token) {
        log.info("returnInitiatorToTask");
        return secFeign.returnInitiatorToTaskDelete(request, token);
    }

    /**
     * 更新专家库的变更状态
     *
     * @param id
     * @return
     */
    @ApiOperation("更新专家库的变更状态")
    @GetMapping("/updateChangeStatus")
    public BaseResponse updateChangeStatus(@RequestParam String id) {
        log.info("updateChangeStatus");
        return secFeign.updateChangeStatusDelete(id);
    }
}
