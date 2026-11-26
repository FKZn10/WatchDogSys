package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecClearTeamDTO;
import com.jpmsoft.projectProducer.service.secInfo.ISecClearTeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/6 11:49
 */
@Slf4j
@RestController
@RequestMapping("/v1/clearTeam")
public class SecClearTeamController {

    @Resource
    private ISecClearTeamService secClearTeamService;


    @PostMapping("/createOrUpdateSecClearTeam")
    public BaseResponse createOrUpdateSecClearTeam(@RequestBody SecClearTeamDTO request) {
        log.info("createOrUpdateSecClearTeam");
        return secClearTeamService.createOrUpdateSecClearTeam(request);
    }

    @GetMapping("/getSecClearTeamById")
    public BaseResponse getSecClearTeamById(@RequestParam String id) {
        log.info("getSecClearTeamById");
        return secClearTeamService.getSecClearTeamById(id);
    }
}
