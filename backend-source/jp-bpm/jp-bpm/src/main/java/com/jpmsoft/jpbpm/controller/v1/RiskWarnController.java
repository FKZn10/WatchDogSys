package com.jpmsoft.jpbpm.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.jpbpm.pojo.entity.RiskWarn;
import com.jpmsoft.jpbpm.service.RiskWarnService;
import com.jpmsoft.jpbpm.util.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Slf4j
@RestController
@ApiIgnore
@Api(tags = "廉政代办", value = "廉政代办")
@RequestMapping("/v1/RiskWarn")
public class RiskWarnController {
    @Autowired
    RiskWarnService riskWarnService;
    @Autowired
    private RedisUtils redisUtils;
    /**
     * 我的事务
     * 流程反馈记录
     *
     * @param request 请求参数
     * @return
     */
    @ApiOperation(value = "生成廉政待办信息")
    @PostMapping("/alertGenerationAgent")
    public BaseResponse alertGenerationAgent(@RequestBody List<RiskWarn> request) {
        log.info("alertGenerationAgent");
        //String customerId=redisUtils.getCustomerId("");
        return riskWarnService.alertGenerationAgent(request,"All");
    }
}
