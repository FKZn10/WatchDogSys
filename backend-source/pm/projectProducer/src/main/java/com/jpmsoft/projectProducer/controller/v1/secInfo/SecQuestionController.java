package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecQuestionDTO;
import com.jpmsoft.projectProducer.service.secInfo.ISecQuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/6 11:49
 */
@Slf4j
@RestController
@RequestMapping("/v1/secQuestion")
public class SecQuestionController {

    @Resource
    private ISecQuestionService secQuestionService;


    @PostMapping("/createOrUpdateSecQuestion")
    public BaseResponse createOrUpdateSecQuestion(@RequestBody SecQuestionDTO request) {
        log.info("createOrUpdateSecQuestion");
        return secQuestionService.createOrUpdateSecQuestion(request);
    }

    @GetMapping("/getSecQuestionById")
    public BaseResponse getSecQuestionById(@RequestParam String id) {
        log.info("getSecQuestionById");
        return secQuestionService.getSecQuestionById(id);
    }
}
