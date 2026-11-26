package com.jpmsoft.projectProducer.controller.v1.supervision;



import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Supervisor.PunishRequest;
import com.jpmsoft.projectProducer.service.supervision.IPunishService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/punish")
public class PunishController {
    @Resource
    private IPunishService punishService;
    @Resource
    private RedisUtils redisUtils;

    @PostMapping("/getPunishList")
    public BaseResponse getPunishList(@RequestBody PunishRequest request, @RequestHeader String token) {
        log.info("getPunishList");
        return punishService.getPunishList(request, redisUtils.getUid(token));
    }
}
