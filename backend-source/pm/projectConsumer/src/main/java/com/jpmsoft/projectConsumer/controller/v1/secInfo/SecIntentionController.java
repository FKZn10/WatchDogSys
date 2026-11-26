package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.secInfo.SecIntentionDTO;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 招标意向
 *
 * @Author: 李文涛 aaron
 * @date: 2024/3/20 16:21
 */
@Slf4j
@RestController
@Api(tags = "招标意向", value = "招标意向")
@RequestMapping("/v1/intention")
public class SecIntentionController {

    @Resource
    private SecFeign secFeign;

    @ApiOperation(value = "创建或修改招标意向")
    @PostMapping("/createOrUpdateSecIntention")
    public BaseResponse createOrUpdateSecIntention(@RequestBody SecIntentionDTO request) {
        log.info("createOrUpdateSecIntention");
        return secFeign.createOrUpdateSecIntention(request);
    }

    @ApiOperation(value = "ID查询招标意向")
    @GetMapping("/getSecIntentionById")
    public BaseResponse getSecIntentionById(@RequestParam String id) {
        log.info("getSecIntentionById");
        return secFeign.getSecIntentionById(id);
    }
}
