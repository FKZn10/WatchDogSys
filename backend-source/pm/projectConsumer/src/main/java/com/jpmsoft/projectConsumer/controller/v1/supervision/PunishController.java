package com.jpmsoft.projectConsumer.controller.v1.supervision;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Supervisor.ConSupPlanRequest;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.jpmsoft.common.model.request.Supervisor.PunishRequest;

@Slf4j
@RestController
@Api(tags = "违规处罚", value = "违规处罚")
@RequestMapping("/v1/punish")
public class PunishController {
    @Autowired
    private BaseFeign baseFeign;

    @ApiOperation(value = "分页查询监理规划及细则")
    @PostMapping("/getPunishList")
    public BaseResponse getPunishList(@RequestBody PunishRequest request, @RequestHeader String token) {
        log.info("getPunishList");
        return baseFeign.getPunishList(request, token);
    }
}
