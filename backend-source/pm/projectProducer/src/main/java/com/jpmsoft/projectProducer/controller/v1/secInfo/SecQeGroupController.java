package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecQeGroupRequest;
import com.jpmsoft.projectProducer.service.secInfo.ISecQeGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Slf4j
@RestController
@RequestMapping("/v1/qeGroup")
public class SecQeGroupController {

    @Resource
    private ISecQeGroupService secQeGroupService;

    @PostMapping("/createOrUpdateSecQeGroup")
    public BaseResponse createOrUpdateSecQeGroup(@RequestBody SecQeGroupRequest request) {
        log.info("createOrUpdateSecQeGroup");
        return secQeGroupService.createOrUpdateSecQeGroup(request);
    }

    @GetMapping("/deleteSecQeGroupByQegId")
    public BaseResponse deleteSecQeGroupByQegId(@RequestParam String qegId) {
        log.info("deleteSecQeGroupByQegId");
        return secQeGroupService.deleteSecQeGroupByQegId(qegId);
    }

    @GetMapping("/getSecQeGroupByQegId")
    public BaseResponse getSecQeGroupByQegId(@RequestParam String qegId) {
        log.info("getSecQeGroupByQegId");
        return secQeGroupService.getSecQeGroupByQegId(qegId);
    }
}
