package com.jpmsoft.jpbpm.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.jpbpm.pojo.dto.AgencyStatisticsDto;
import com.jpmsoft.jpbpm.pojo.vo.AgencyStatisticsVo;
import com.jpmsoft.jpbpm.service.CarbonCopyService;
import com.jpmsoft.jpbpm.service.SysCoordinationTaskService;
import com.jpmsoft.jpbpm.service.TaskService;
import com.jpmsoft.jpbpm.util.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/26 11:07
 */
@Slf4j
@RestController
@Api(tags = "用户代办", value = "用户代办")
@RequestMapping("/v1/userTask")
public class UsetTaskController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private CarbonCopyService carbonCopyService;
    @Autowired
    private SysCoordinationTaskService sysCoordinationTaskService;
    @Autowired
    private RedisUtils redisUtils;

    @ApiOperation(value = "代办统计")
    @PostMapping("/agencyStatistics")
    public BaseResponse agencyStatistics(@RequestBody @Validated AgencyStatisticsDto request,@RequestHeader(value ="token",required = false) String token) {
        String customerId=redisUtils.getCustomerId(token);
        request.setTCodes(request.getTCodes().stream().distinct().collect(Collectors.toList()));
        List<AgencyStatisticsVo> tasks = taskService.statisticsUserAgency(request,customerId);
        List<AgencyStatisticsVo> carbons = carbonCopyService.statisticsUserAgency(request,customerId);
        List<AgencyStatisticsVo> coors = sysCoordinationTaskService.statisticsUserAgency(request,customerId);
        List<AgencyStatisticsVo> datas = new ArrayList<>();
        for (String tCode : request.getTCodes()) {
            AgencyStatisticsVo data = new AgencyStatisticsVo();
            data.setTCode(tCode);
            data.setTotal(getTotal(tasks, tCode) + getTotal(carbons, tCode) + getTotal(coors, tCode));
            datas.add(data);
        }
        return BaseResponse.ok(datas);
    }

    private Integer getTotal(List<AgencyStatisticsVo> datas, String code) {
        if (CollectionUtils.isEmpty(datas)) return 0;
        List<AgencyStatisticsVo> data = datas.stream().filter(n -> n.getTCode().equals(code)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(data)) return 0;
        return data.get(0).getTotal();
    }
}
