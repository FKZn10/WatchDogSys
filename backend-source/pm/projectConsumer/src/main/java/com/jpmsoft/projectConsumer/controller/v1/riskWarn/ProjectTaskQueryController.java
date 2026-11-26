package com.jpmsoft.projectConsumer.controller.v1.riskWarn;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.RiskWarn.RiskWarQueryRequest;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "（风险预警）项目风险信息统计", value = "（风险预警）项目风险信息统计")
@RequestMapping("/riskWarn/v1/projectTaskQuery")
public class ProjectTaskQueryController {
    @Autowired
    private BaseFeign baseFeign;

    /**
     * 获取项目风险预警信息
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "获取项目风险预警信息")
    @PostMapping("/getProjectTaskQueryCount")
    public BaseResponse getProjectTaskQueryCount(@RequestBody RiskWarQueryRequest request, @RequestHeader String token) {
        log.info("getProjectTaskQueryCount");
        return baseFeign.getProjectTaskQueryCount(request, token);
    }
}
