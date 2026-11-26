package com.jpmsoft.projectConsumer.controller.v1.other;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.ProjectActivityDto;
import com.jpmsoft.common.model.request.ProgressStatisticsRequest;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/monthlyProgress")
public class MonthlyProgressController {

    @Resource
    private BaseFeign baseFeign;

    @GetMapping(value = "/getContractAmount")
    public BaseResponse getContractAmount(@RequestParam String year, @RequestParam String month, @RequestParam String pId) {
        return baseFeign.getContractAmount(year, month,pId);
    }

    /**
     * 政府投资工程项目 进展情况统计表
     *
     * @param request
     * @param token
     * @return
     */
    @ApiOperation("政府投资工程项目 进展情况统计表")
    @PostMapping("/getProgressStatisticsList")
    public BaseResponse getProgressStatisticsList(@RequestBody ProgressStatisticsRequest request, @RequestHeader String token) {
        log.info("getProgressStatisticsList");
        return baseFeign.getProgressStatisticsList(request, token);
    }
    @GetMapping(value = "/selectAreaAndValue")
    public BaseResponse selectAreaAndValue(@RequestParam String year, @RequestParam String month, @RequestParam String oId) {
        return baseFeign.selectAreaAndValue(year, month,oId);

    }
    @PostMapping(value = "/getProjectActivity")
    public BaseResponse getProjectActivity(@RequestBody ProjectActivityDto request, @RequestHeader String token) {
        return baseFeign.getProjectActivity(request,token);
    }

}
