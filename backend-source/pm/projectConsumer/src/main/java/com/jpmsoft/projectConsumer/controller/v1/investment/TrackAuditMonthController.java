package com.jpmsoft.projectConsumer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.feign.InvFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author: shanzhi
 * @date: 2024/06/28 17:10
 */
@Slf4j
@RestController
@Api(tags = "跟踪审计月报", value = "跟踪审计月报")
@RequestMapping("/v1/trackAuditMonth")
public class TrackAuditMonthController {

    @Resource
    private InvFeign invFeign;

    @ApiOperation(value = "根据合同ID查询累计金额")
    @GetMapping("/getTrackAuditMonthByConId")
    public BaseResponse getTrackAuditMonthByConId(@RequestParam String conId,@RequestParam(value = "month", required = false) String month) {
        log.info("getTrackAuditMonthByConId");
        return invFeign.getTrackAuditMonthByConId(conId,month);
    }

    @ApiOperation(value = "根据项目ID获取台账信息")
    @GetMapping("/getTrackAuditMonthListByPId")
    public BaseResponse getTrackAuditMonthListByPId(@RequestParam String pId) {
        log.info("getTrackAuditMonthListByPId");
        return invFeign.getTrackAuditMonthListByPId(pId);
    }

    @ApiOperation(value = "流程回调")
    @GetMapping("/approvalTrackAuditMonth")
    public BaseResponse approvalTrackAuditMonth(@RequestParam String tamId) {
        log.info("approvalTrackAuditMonth");
        return invFeign.approvalTrackAuditMonth(tamId);
    }


}
