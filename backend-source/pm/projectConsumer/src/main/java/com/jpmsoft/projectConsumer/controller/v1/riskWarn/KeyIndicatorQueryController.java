package com.jpmsoft.projectConsumer.controller.v1.riskWarn;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.RiskWarn.RiskWarnIndexRequest;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "（风险预警）风险预警信息", value = "（风险预警）风险预警信息")
@RequestMapping("/riskWarn/v1/keyIndicatorQuery")
public class KeyIndicatorQueryController {
    @Autowired
    private BaseFeign baseFeign;

    /**
     * 获取风险预警指标生成的数据
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "获取风险预警指标生成的数据")
    @PostMapping("/getKeyIndicatorQuery")
    public BaseResponse getKeyIndicatorQuery(@RequestBody RiskWarnIndexRequest request, @RequestHeader String token) {
        log.info("getKeyIndicatorQuery");
        return baseFeign.getKeyIndicatorQuery(request, token);
    }
}
