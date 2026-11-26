package com.jpmsoft.projectConsumer.controller.v1.riskWarn;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.RiskWarn.RiskWarnRequest;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "风险预警信息", value = "风险预警信息")
@RequestMapping("/v1/riskWarn")
public class RiskWarnController {
    @Autowired
    private BaseFeign baseFeign;

    @ApiOperation(value = "分页查询风险预警信息")
    @PostMapping("/getRiskWarnList")
    public BaseResponse getRiskWarnList(@RequestBody RiskWarnRequest request, @RequestHeader String token) {
        log.info("getRiskWarnList");
        return baseFeign.getRiskWarnList(request, token);
    }

    /**
     * 查询风险预警数量排名，按 rwiId 分组，仅使用 size 限制条数。
     *
     * @param request 筛选条件和排名条数
     * @param token   登录令牌
     * @return 按数量倒序排列的预警名称与数量 Map
     */
    @ApiOperation(value = "查询风险预警排名")
    @PostMapping("/getRiskWarnRanking")
    public BaseResponse getRiskWarnRanking(@RequestBody RiskWarnRequest request, @RequestHeader String token) {
        log.info("getRiskWarnRanking");
        return baseFeign.getRiskWarnRanking(request, token);
    }

    @ApiOperation(value = "ID查询风险预警信息")
    @GetMapping("/getRiskWarnById")
    public BaseResponse getRiskWarnById(@RequestParam String rwId) {
        log.info("getRiskWarnById");
        return baseFeign.getRiskWarnById(rwId);
    }

    @ApiOperation(value = "分页查询风险预警信息-项目管理大纲体系")
    @PostMapping("/getRiskWarnListOutline")
    public BaseResponse getRiskWarnListOutline(@RequestBody RiskWarnRequest request, @RequestHeader String token) {
        log.info("getRiskWarnListOutline");
        return baseFeign.getRiskWarnListOutline(request, token);
    }
}
