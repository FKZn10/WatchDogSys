package com.jpmsoft.projectConsumer.controller.v1.riskWarn;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.riskWarn.RiskPreventionMeasureDTO;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "风险点防控措施", value = "风险点防控措施")
@RequestMapping("/riskWarn/v1/riskPreventionMeasure")
public class RiskPreventionMeasureController {

    @Resource
    private BaseFeign baseFeign;

    @ApiOperation(value = "新增修改风险点防控措施")
    @PostMapping("/createOrUpdate")
    public BaseResponse createOrUpdate(@RequestBody RiskPreventionMeasureDTO request) {
        return baseFeign.createOrUpdate(request);
    }

    @ApiOperation(value = "删除风险点防控措施")
    @GetMapping("/delete")
    public BaseResponse delete(@RequestParam String id) {
        return baseFeign.delete(id);
    }

    @ApiOperation(value = "启用禁用风险点防控措施")
    @GetMapping("/enablePreventionMeasure")
    public BaseResponse enablePreventionMeasure(@RequestParam String id, @RequestParam String status) {
        return baseFeign.enablePreventionMeasure(id, status);
    }

    @ApiOperation(value = "获取风险点防控措施树")
    @GetMapping("/getTree")
    public BaseResponse getTree(@RequestHeader String token) {
        return baseFeign.getTree(token);
    }

    @ApiOperation(value = "上下移动风险点防控措施（ up上移 down下移）")
    @GetMapping("/upOrDown")
    public BaseResponse upOrDown(@RequestParam String id, @RequestParam String action) {
        return baseFeign.upOrDown(id, action);
    }

}
