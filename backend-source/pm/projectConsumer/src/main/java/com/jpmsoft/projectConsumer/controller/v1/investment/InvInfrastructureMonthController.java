package com.jpmsoft.projectConsumer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.investment.InvInfrastructureMonthDto;
import com.jpmsoft.projectConsumer.pojo.dto.investment.InvInfrastructureQueryDto;
import com.jpmsoft.projectConsumer.feign.InvFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/5 11:13
 */
@Slf4j
@RestController
@Api(tags = "基建投资月报", value = "基建投资月报")
@RequestMapping("/v1/invInfrastructureMonth")
public class InvInfrastructureMonthController {

    @Autowired
    private InvFeign invFeign;

    @ApiOperation(value = "新增或修改基建投资月报")
    @PostMapping("/createOrUpdateInvInfrastructureMonth")
    public BaseResponse createOrUpdateInvInfrastructureMonth(@RequestBody InvInfrastructureMonthDto request) {
        log.info("createOrUpdateInvInfrastructureMonth");
        return invFeign.createOrUpdateInvInfrastructureMonth(request);
    }

    @ApiOperation(value = "ID查询基建投资月报")
    @GetMapping("/getInvInfrastructureMonthById")
    public BaseResponse getInvInfrastructureMonthById(@RequestParam String id) {
        log.info("getInvInfrastructureMonthById");
        return invFeign.getInvInfrastructureMonthById(id);
    }

    @ApiOperation(value = "分页查询基建投资月报清单")
    @PostMapping("/getInvInfrastructureMonthDtlList")
    public BaseResponse getInvInfrastructureMonthDtlList(@RequestBody InvInfrastructureQueryDto request, @RequestHeader String token) {
        log.info("getInvInfrastructureMonthDtlList");
        return invFeign.getInvInfrastructureMonthDtlList(request, token);
    }
}
