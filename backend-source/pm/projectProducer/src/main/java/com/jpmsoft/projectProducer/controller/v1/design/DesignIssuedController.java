package com.jpmsoft.projectProducer.controller.v1.design;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Design.DesignResultsIssuedRequest;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignIssuedModel;
import com.jpmsoft.projectProducer.service.design.IDesignIssuedService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/designIssued")
public class DesignIssuedController {

    @Resource
    private IDesignIssuedService designResultsIssuedService;

    @Resource
    private RedisUtils redisUtils;

    @PostMapping("/getDesignIssuedList")
    public BaseResponse getDesignIssuedList(@RequestBody DesignIssuedModel request, @RequestHeader String token) {
        return designResultsIssuedService.getDesignIssuedList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getDesignIssuedById")
    public BaseResponse getDesignIssuedById(@RequestParam String id, @RequestHeader String token) {
        return designResultsIssuedService.getDesignIssuedById(id, redisUtils.getUid(token));
    }


}
