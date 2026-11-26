package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.secInfo.SecQuestionDTO;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/6 11:49
 */
@Slf4j
@RestController
@Api(tags = "质询", value = "质询")
@RequestMapping("/v1/secQuestion")
public class SecQuestionController {

    @Resource
    private SecFeign secFeign;

    @ApiOperation(value = "创建或修改质询")
    @PostMapping("/createOrUpdateSecQuestion")
    public BaseResponse createOrUpdateSecQuestion(@RequestBody SecQuestionDTO request) {
        log.info("createOrUpdateSecQuestion");
        return secFeign.createOrUpdateSecQuestion(request);
    }

    @ApiOperation(value = "ID查询质询")
    @GetMapping("/getSecQuestionById")
    public BaseResponse getSecQuestionById(@RequestParam String id) {
        log.info("getSecQuestionById");
        return secFeign.getSecQuestionById(id);
    }
}
