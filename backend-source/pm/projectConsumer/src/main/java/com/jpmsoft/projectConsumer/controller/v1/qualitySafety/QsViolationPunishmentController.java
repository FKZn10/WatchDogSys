package com.jpmsoft.projectConsumer.controller.v1.qualitySafety;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Qs.QsViolationPunishmentRequest;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "质量安全违规处罚", value = "质量安全违规处罚")
@RequestMapping("/safety/v1/qsViolation")
public class QsViolationPunishmentController {
    @Autowired
    private BaseFeign baseFeign;

    @ApiOperation(value = "创建或修改质量安全违规处罚")
    @PostMapping("/createOrUpdateQsViolationPunishment")
    public BaseResponse createOrUpdateQsViolationPunishment(@RequestBody QsViolationPunishmentRequest request) {
        log.info("createOrUpdateQsViolationPunishment");
        return baseFeign.createOrUpdateQsViolationPunishment(request);
    }

    @ApiOperation(value = "分页查询质量安全违规处罚")
    @PostMapping("/getQsViolationPunishmentList")
    public BaseResponse getQsViolationPunishmentList(@RequestBody QsViolationPunishmentRequest request, @RequestHeader String token) {
        log.info("getQsViolationPunishmentList");
        return baseFeign.getQsViolationPunishmentList(request, token);
    }

    @ApiOperation(value = "ID查询质量安全违规处罚")
    @GetMapping("/getQsViolationPunishmentById")
    public BaseResponse getQsViolationPunishmentById(@RequestParam String vpId) {
        log.info("getQsViolationPunishmentById");
        return baseFeign.getQsViolationPunishmentById(vpId);
    }
}
