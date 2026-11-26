package com.jpmsoft.projectConsumer.controller.v1.design;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignIssuedModel;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/designIssued")
public class DesignIssuedController {

    @Resource
    private BaseFeign baseFeign;

    @PostMapping("/getDesignIssuedList")
    public BaseResponse getDesignIssuedList(@RequestBody DesignIssuedModel request,@RequestHeader String token) {
        return baseFeign.getDesignIssuedList(request, token);
    }

    @GetMapping("/getDesignIssuedById")
    public BaseResponse getDesignIssuedById(@RequestParam String id,@RequestHeader String token) {
        return baseFeign.getDesignIssuedById(id, token);
    }

}
