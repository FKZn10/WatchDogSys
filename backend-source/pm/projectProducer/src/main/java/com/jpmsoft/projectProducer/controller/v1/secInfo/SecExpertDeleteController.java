package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecExpertDeleteModel;
import com.jpmsoft.projectProducer.pojo.request.ReturnInitiatorDto;
import com.jpmsoft.projectProducer.service.secInfo.ISecExpertDeleteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 专家删除申请
 */
@Slf4j
@RestController
@RequestMapping("/v1/secExpertDelete")
public class SecExpertDeleteController {
    @Resource
    private ISecExpertDeleteService secExpertDeleteService;

    @PostMapping("/createOrUpdateSecExpertDelete")
    public BaseResponse createOrUpdateSecExpertDelete(@RequestBody SecExpertDeleteModel request, @RequestHeader String token) {
        log.info("createOrUpdateSecExpertDelete");
        return secExpertDeleteService.createOrUpdateSecExpertDelete(request, token);
    }

    @PostMapping("/getSecExpertDeleteList")
    public BaseResponse getSecExpertDeleteList(@RequestBody SecExpertDeleteModel request) {
        log.info("getSecExpertDeleteList");
        return secExpertDeleteService.getSecExpertDeleteList(request);
    }

    @GetMapping("/getSecExpertDeleteById")
    public BaseResponse getSecExpertDeleteById(@RequestParam String id) {
        log.info("getSecExpertDeleteById");
        return secExpertDeleteService.getSecExpertDeleteById(id);
    }

    @GetMapping("/syncSecExpertDeleteData")
    public BaseResponse syncSecExpertDeleteData(@RequestParam String id) {
        log.info("syncSecExpertDeleteData");
        return secExpertDeleteService.syncSecExpertDeleteData(id);
    }

    @PostMapping("/returnInitiatorToTask")
    public BaseResponse returnInitiatorToTask(@RequestBody ReturnInitiatorDto request, @RequestHeader String token) {
        log.info("returnInitiatorToTask");
        return secExpertDeleteService.returnInitiatorToTask(request, token);
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
        return secExpertDeleteService.updateChangeStatus(id);
    }
}
