package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecExpertChangeModel;
import com.jpmsoft.projectProducer.pojo.request.ReturnInitiatorDto;
import com.jpmsoft.projectProducer.service.secInfo.ISecExpertChangeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/secExpertChange")
public class SecExpertChangeController {
    @Resource
    private ISecExpertChangeService secExpertChangeService;

    @PostMapping("/createOrUpdateSecExpertChange")
    public BaseResponse createOrUpdateSecExpertChange(@RequestBody SecExpertChangeModel request, @RequestHeader String token) {
        log.info("createOrUpdateSecExpertChange");
        return secExpertChangeService.createOrUpdateSecExpertChange(request, token);
    }

    @PostMapping("/getSecExpertChangeList")
    public BaseResponse getSecExpertChangeList(@RequestBody SecExpertChangeModel request) {
        log.info("getSecExpertChangeList");
        return secExpertChangeService.getSecExpertChangeList(request);
    }

    @GetMapping("/getSecExpertChangeById")
    public BaseResponse getSecExpertChangeById(@RequestParam String id) {
        log.info("getSecExpertChangeById");
        return secExpertChangeService.getSecExpertChangeById(id);
    }

    @GetMapping("/syncSecExpertChangeData")
    public BaseResponse syncSecExpertChangeData(@RequestParam String id) {
        log.info("syncSecExpertChangeData");
        return secExpertChangeService.syncSecExpertChangeData(id);
    }

    @PostMapping("/returnInitiatorToTask")
    public BaseResponse returnInitiatorToTask(@RequestBody ReturnInitiatorDto request, @RequestHeader String token) {
        log.info("returnInitiatorToTask");
        return secExpertChangeService.returnInitiatorToTask(request, token);
    }

    /**
     * 更新专家库的变更状态
     *
     * @param id
     * @return
     */
    @GetMapping("/updateChangeStatus")
    public BaseResponse updateChangeStatus(@RequestParam String id) {
        log.info("updateChangeStatus");
        return secExpertChangeService.updateChangeStatus(id);
    }
}
