package com.jpmsoft.projectProducer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.InvestmentControl.InvCompleteMonthRequest;
import com.jpmsoft.common.model.request.Plan.GanttPlanRequest;
import com.jpmsoft.common.model.request.Plan.GanttTasksProcessRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.investment.IInvPlanYearService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/invMonthen")
public class writeBackPlanController {

    /**
     * 根据年度投资计划，回写月度投资计划
     */
    @Resource
    private IInvPlanYearService invPlanYear;

    @Resource
    private RedisUtils redisUtils;

    @GetMapping("/writeBackMonthPlanId")
    public BaseResponse writeBackMonthPlan(@RequestParam String ipvId, @RequestHeader String token){
        log.info("pp : writeBackMonthPlan   , ipvId" + ipvId + " token :" + token);
        return invPlanYear.writeBackMonthPlan(ipvId,redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getMonthplanClassInfo")
    public BaseResponse getMonthplanClassInfo(@RequestBody InvCompleteMonthRequest request, @RequestHeader String token) {
        log.info("pp getGanttPlanClassInfo  request : " + request + "token :" + token);
        return invPlanYear.getMonthplanClassInfo(request, redisUtils.getUserInfoByToken(token));
    }

    @TenantId
    @PostMapping("/getCompleteMonthplanList")
    public BaseResponse getMonthplanList(@RequestBody InvCompleteMonthRequest request, @RequestHeader String token) {
        log.info("pp  getMonthplanList request : " + request + "token : " + token);
        return invPlanYear.getMonthplanList(request, redisUtils.getUserInfoByToken(token));
    }
}
