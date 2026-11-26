package com.jpmsoft.projectConsumer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.investment.FundPlanModifyDto;
import com.jpmsoft.projectConsumer.feign.InvFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "资金计划下达浏览", value = "资金计划下达浏览")
@RequestMapping("/v1/fundPlanModify")
public class FundPlanModifyController {
    @Autowired
    private InvFeign invFeign;

    @ApiOperation(value = "新增资金计划维护信息")
    @GetMapping("/createFundPlanModify")
    public BaseResponse createFundPlanModify(@RequestParam String ifrId) {
        log.info("createFundPlanModify");
        return invFeign.createFundPlanModify(ifrId);
    }


    @ApiOperation(value = "根据项目id查询资金计划下达信息")
    @GetMapping("/getFundPlanModifyBypPid")
    public BaseResponse getFundPlanModifyBypPid(@RequestParam String pId) {
        log.info("getFundPlanModifyBypPid");
        return invFeign.getFundPlanModifyBypPid(pId);
    }

    @ApiOperation(value = "分页查询资金计划申下达浏览信息")
    @PostMapping("/getFundPlanModifyList")
    public BaseResponse getFundPlanModifyList(@RequestBody FundPlanModifyDto request, @RequestHeader String token) {
        log.info("getFundPlanModifyList");
        return invFeign.getFundPlanModifyList(request, token);
    }

    @ApiOperation(value = "修改资金计划下达维护信息")
    @PostMapping("/updateFundPlanModify")
    public BaseResponse updateFundPlanModify(@RequestBody FundPlanModifyDto request) {
        log.info("updateFundPlanModify");
        return invFeign.updateFundPlanModify(request);
    }

    @ApiOperation(value = "根据年度季度获取资金计划下达信息")
    @GetMapping("/getFundPlanModifyByPidAndYear")
    public BaseResponse getFundPlanModifyByPidAndYear(@RequestParam String pId, @RequestParam Integer year,@RequestParam String  newDate) {
        log.info("getFundPlanModifyByPidAndYear");
        return invFeign.getFundPlanModifyByPidAndYear(pId, year,newDate);
    }



    @ApiOperation(value = "根据项目id查询资金计划下达信息")
    @GetMapping("/getFundPlanModifyHandleBypPid")
    public BaseResponse getFundPlanModifyHandleBypPid(@RequestParam String pId) {
        log.info("getFundPlanModifyHandleBypPid");
        return invFeign.getFundPlanModifyHandleBypPid(pId);
    }
}
