package com.jpmsoft.jpbpm.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.jpbpm.pojo.dto.HolidayVacationsDto;
import com.jpmsoft.jpbpm.pojo.dto.NextActivitiesDto;
import com.jpmsoft.jpbpm.service.FlowService;
import com.jpmsoft.jpbpm.service.IHolidayVacationsService;
import com.jpmsoft.jpbpm.util.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "流程组件", value = "流程组件")
@RequestMapping("/v1/HolidayVacations")
public class HolidayVacationsController {
    @Autowired
    private IHolidayVacationsService holidayVacationsService;
    @Autowired
    private RedisUtils redisUtils;

    /**
     * 获取下一步流程节点用户信息
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "工作日计算")
    @PostMapping("/computingTime")
    public BaseResponse computingTime(@RequestBody HolidayVacationsDto request, @RequestHeader(value = "token", required = false) String token) {
        log.info("computingTime");
        String customerId = redisUtils.getCustomerId(token);
        String result = holidayVacationsService.computingTime(request, customerId);
        log.info("result:{}", result);
        return BaseResponse.ok(result);
    }
}
