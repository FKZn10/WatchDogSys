package com.jpmsoft.projectProducer.controller.v1.riskWarn;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.riskWarn.RiskPreventionMeasureDTO;
import com.jpmsoft.projectProducer.service.riskWarn.IRiskPreventionMeasureService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 风险点防控措施
 *
 * @Author: 李文涛 aaron
 * @date: 2023/3/29 12:01
 */
@Slf4j
@RestController
@RequestMapping("/riskWarn/v1/riskPreventionMeasure")
public class RiskPreventionMeasureController {

    @Resource
    private IRiskPreventionMeasureService iRiskPreventionMeasureService;
    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdate")
    public BaseResponse createOrUpdate(@RequestBody RiskPreventionMeasureDTO request) {
        return iRiskPreventionMeasureService.createOrUpdate(request);
    }

    @GetMapping("/delete")
    public BaseResponse delete(@RequestParam String id) {
        return iRiskPreventionMeasureService.delete(id);
    }

    @GetMapping("/enablePreventionMeasure")
    public BaseResponse enablePreventionMeasure(@RequestParam String id, @RequestParam String status) {
        return iRiskPreventionMeasureService.enable(id, status);
    }

    @GetMapping("/getTree")
    public BaseResponse getTree(@RequestHeader String token) {
        return iRiskPreventionMeasureService.getTree(redisUtils.getUserInfoByToken(token));
    }

    @GetMapping("/upOrDown")
    public BaseResponse upOrDown(@RequestParam String id, @RequestParam String action, @RequestHeader String token) {
        return iRiskPreventionMeasureService.upOrDown(id, action,redisUtils.getTeId(token));
    }

}
