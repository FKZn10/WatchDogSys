package com.jpmsoft.jpbpm.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.jpbpm.service.SysTaskProcessinTimeService;
import com.jpmsoft.jpbpm.util.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@Api(tags = "审批时效", value = "审批时效")
@RequestMapping("/v1/SysTaskProcessinTime")
public class SysTaskProcessinTimeController {
    @Autowired
    SysTaskProcessinTimeService sysTaskProcessinTimeService;
    @Autowired
    private RedisUtils redisUtils;

    @ApiOperation(value = "批量同步审批时效")
    @GetMapping("/synchronizationTaskProcessinTime")
    public BaseResponse synchronizationTaskProcessinTime(@RequestParam String startDate,@RequestParam String endDate, @RequestParam(value ="tCode",required = false) String tCode,@RequestParam(value ="year",required = false) String  year,@RequestHeader(value ="token",required = false) String token) {
        log.info("synchronizationTaskProcessinTime");
        String customerId=redisUtils.getCustomerId(token);
        return sysTaskProcessinTimeService.synchronizationTaskProcessinTime(startDate,endDate,customerId,tCode,year);
    }


    @ApiOperation(value = "根据id插叙审批时效")
    @GetMapping("/getSysTaskProcessinTimeByTpId")
    public BaseResponse getSysTaskProcessinTimeByTpId(@RequestParam String tpId,@RequestHeader(value ="token",required = false) String token) {
        log.info("getSysTaskProcessinTimeByTpId");
        String customerId=redisUtils.getCustomerId(token);
        return sysTaskProcessinTimeService.getSysTaskProcessinTimeByTpId(tpId,customerId);
    }

    @ApiOperation(value = "根据审批记录id修改审批时效")
    @GetMapping("/updateSysTaskProcessinTimeByTpId")
    public BaseResponse updateSysTaskProcessinTimeByTpId(@RequestParam String tpId,@RequestParam String ageing,@RequestParam(value ="tCode",required = false) String tCode,@RequestParam(value ="year",required = false) String  year,@RequestHeader(value ="token",required = false) String token) {
        log.info("updateSysTaskProcessinTimeByTpId");
        String customerId=redisUtils.getCustomerId(token);
        return sysTaskProcessinTimeService.updateSysTaskProcessinTimeByTpId(tpId,ageing,tCode,customerId,year);
    }

    @ApiOperation(value = "根据tpid集合查询审批时效信息")
    @PostMapping("/getSysTaskProcessinTimeByTpIds")
    public BaseResponse getSysTaskProcessinTimeByTpIds(@RequestBody List<String>  tpId,@RequestHeader(value ="token",required = false) String token) {
        log.info("getSysTaskProcessinTimeByTpIds");
        String customerId=redisUtils.getCustomerId(token);
        return sysTaskProcessinTimeService.getSysTaskProcessinTimeByTpIds(tpId,customerId);
    }



    @ApiOperation(value = "批量刷新审批时效里的fileCOde")
    @GetMapping("/updateFilecodeByFiles")
    public BaseResponse updateFilecodeByFiles(@RequestParam String startDate,@RequestParam String endDate,@RequestHeader(value ="token",required = false) String token) {
        log.info("updateFilecodeByFiles");
        String customerId=redisUtils.getCustomerId(token);
        return sysTaskProcessinTimeService.updateFilecodeByFiles(startDate,endDate,customerId);
    }
    @ApiOperation(value = "单条修改审批时效里的fileCOde")
    @GetMapping("/updateFilecodeByFile")
    public BaseResponse updateFilecodeByFile(@RequestParam String code,@RequestParam String file, @RequestParam(value ="tCode",required = false) String tCode,@RequestParam(value ="year",required = false) String  year,@RequestHeader(value ="token",required = false) String token) {
        log.info("updateFilecodeByFile");
        String customerId=redisUtils.getCustomerId(token);
        return sysTaskProcessinTimeService.updateFilecodeByFile(code,file,customerId,tCode,year);
    }


    @ApiOperation(value = "批量修改被驳回人的信息")
    @GetMapping("/rejectInnformationCorrection")
    public BaseResponse  rejectInnformationCorrection(String startDate, String endDate, @RequestHeader(value ="token",required = false) String token){
        log.info("rejectInnformationCorrection");
        String customerId=redisUtils.getCustomerId(token);
        return sysTaskProcessinTimeService.rejectInnformationCorrection(startDate,endDate,customerId);
    }
}
