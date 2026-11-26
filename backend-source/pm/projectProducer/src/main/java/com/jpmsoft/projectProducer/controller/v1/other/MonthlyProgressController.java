package com.jpmsoft.projectProducer.controller.v1.other;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.ProgressStatisticsRequest;
import com.jpmsoft.common.model.requestAndResponse.Project.ProjectActivityDto;
import com.jpmsoft.projectProducer.service.other.IMonthlyProgressService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/monthlyProgress")
public class MonthlyProgressController {

    @Resource
    private IMonthlyProgressService iMonthlyProgressService;

    @Resource
    private RedisUtils redisUtils;

    @GetMapping(value = "/getContractAmount")
    public BaseResponse getContractAmount(@RequestParam String year, @RequestParam String month, @RequestParam String pId) {
        log.info("getContractAmount");
        return iMonthlyProgressService.getContractAmount(year, month,pId);
    }

    // 统计项目活动情况
    @PostMapping(value = "/getProjectActivity")
    public BaseResponse getProjectActivity(@RequestBody ProjectActivityDto request, @RequestHeader String token) {
        return iMonthlyProgressService.getProjectActivity(request,redisUtils.getUid(token));
    }

    @GetMapping(value = "/selectAreaAndValue")
    public BaseResponse selectAreaAndValue(@RequestParam String year, @RequestParam String month, @RequestParam String oId) {
        log.info("selectAreaAndValue");
        return iMonthlyProgressService.selectAreaAndValue(year, month,oId);

    }

    /**
     * 政府投资工程项目 进展情况统计表
     *
     * @param request
     * @param token
     * @return
     */
    @PostMapping("/getProgressStatisticsList")
    public BaseResponse getProgressStatisticsList(@RequestBody ProgressStatisticsRequest request, @RequestHeader String token) {
        log.info("getProgressStatisticsList");
        return iMonthlyProgressService.getProgressStatisticsList(request, token);
    }
}
