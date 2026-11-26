package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysLoggerInfoDto;
import com.jpmsoft.userProducer.service.ISysLoggerInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@Api(tags = "日志", value = "日志")
@RestController
@RequestMapping("/v1/SystemManage")
public class SysLoggerInfoController {
    @Resource
    private ISysLoggerInfoService sysLoggerInfoService;

    /**
     * 查询所有的日志信息
     *
     * @return
     */
    @ApiOperation(value = "查询所有的日志信息")
    @PostMapping("/GetLoggerInfoList")
    public BaseResponse GetLoggerInfoList(@RequestBody SysLoggerInfoDto request) {
        log.info("GetLoggerInfoList");
        return sysLoggerInfoService.GetLoggerInfoList(request);
    }


    /**
     * 新增日志信息
     *
     * @return
     */
    @ApiOperation(value = "新增日志信息")
    @PostMapping("/CreateLoggerInfo")
    public BaseResponse CreateLoggerInfo(@RequestBody SysLoggerInfoDto request) {
        log.info("DeleteLoggerInfo");
        return sysLoggerInfoService.insertSysLoggerInfos(request);
    }


    /**
     * 删除日志信息
     *
     * @return
     */
    @ApiOperation(value = "删除日志信息")
    @PostMapping("/DeleteLoggerInfo")
    public BaseResponse DeleteLoggerInfo(@RequestBody SysLoggerInfoDto request) {
        log.info("DeleteLoggerInfo");
        return sysLoggerInfoService.DeleteLoggerInfo(request);
    }

    @PostMapping("/refreshIfIdAndMode")
    public BaseResponse refreshIfIdAndMode(@RequestParam Integer size) {
        log.info("refreshIfIdAndMode");
        return sysLoggerInfoService.refreshIfIdAndMode(size);
    }


    @ApiOperation("导入低代码接口")
    @GetMapping("/importLowInterface")
    public BaseResponse importLowInterface(@RequestHeader String token){
       return sysLoggerInfoService.importLowInterface(token);
    }

}
