package com.jpmsoft.projectConsumer.controller.v1.integratedWork;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 三联单
 */
@Slf4j
@RestController
@RequestMapping("/v1/integratedWork")
public class IntegratedWorkOrderController {
    @Resource
    private BaseFeign baseFeign;

    /**
     * 获取上周工作周计划
     *
     * @param conConstructionId 施工合同ID
     * @param conSupervisionId  监理合同ID
     * @param weeklyStartDate   周计划开始日期
     * @param id                三联单主表ID
     * @return
     */
    @GetMapping("/getLastWeekOrders")
    public BaseResponse getLastWeekOrders(
            @RequestParam("conConstructionId") String conConstructionId,
            @RequestParam("conSupervisionId") String conSupervisionId,
            @RequestParam("weeklyStartDate") String weeklyStartDate,
            @RequestParam(value = "id", required = false) String id
    ) {
        log.info("getLastWeekOrders");
        return baseFeign.getLastWeekOrders(conConstructionId, conSupervisionId, weeklyStartDate, id);
    }
}