package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecCommitteeCompositionDTO;
import com.jpmsoft.projectProducer.service.secInfo.ISecCommitteeCompositionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/6 11:49
 */
@Slf4j
@RestController
@RequestMapping("/v1/committeeComposition")
public class SecCommitteeCompositionController {

    @Resource
    private ISecCommitteeCompositionService secCommitteeCompositionService;


    @PostMapping("/createOrUpdateSecCommitteeComposition")
    public BaseResponse createOrUpdateSecCommitteeComposition(@RequestBody SecCommitteeCompositionDTO request) {
        log.info("createOrUpdateSecCommitteeComposition");
        return secCommitteeCompositionService.createOrUpdateSecCommitteeComposition(request);
    }

    @GetMapping("/getSecCommitteeCompositionById")
    public BaseResponse getSecCommitteeCompositionById(@RequestParam String id) {
        log.info("getSecCommitteeCompositionById");
        return secCommitteeCompositionService.getSecCommitteeCompositionById(id);
    }
}
