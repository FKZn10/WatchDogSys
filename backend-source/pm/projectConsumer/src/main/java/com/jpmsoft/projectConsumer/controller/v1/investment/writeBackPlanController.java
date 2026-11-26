package com.jpmsoft.projectConsumer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.InvestmentControl.InvCompleteMonthRequest;
import com.jpmsoft.projectConsumer.feign.InvFeign;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@Api(tags = "月度计划", value = "月度")
@RequestMapping("/v1/invMonthen")
public class writeBackPlanController {
    @Resource
    private InvFeign invFeign;

    @GetMapping("/writeBackMonthPlan")
    public BaseResponse writeBackMonthPlan(@RequestParam String ipvId){
        log.info("pc : writeBackMonthPlan   , ipvId" + ipvId);
        return invFeign.writeBackMonthPlan(ipvId);
    }

    @PostMapping("/getMonthplanClassInfo")
    public BaseResponse getMonthplanClassInfo(@RequestBody InvCompleteMonthRequest request){
        log.info("pc : writeBackMonthPlan   , request" + request);
        return invFeign.getMonthplanClassInfo(request);
    }

    @PostMapping("/getCompleteMonthplanList")
    public BaseResponse getMonthplanList(@RequestBody InvCompleteMonthRequest request){
        log.info("pc : getCompleteMonthplanList   , request" + request);
        return invFeign.getMonthplanList(request);
    }
}
