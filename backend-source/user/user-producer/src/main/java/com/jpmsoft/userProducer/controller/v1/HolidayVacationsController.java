package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.entity.HolidayVacations;
import com.jpmsoft.userProducer.service.IHolidayVacationsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@Api(tags = "节假日设置", value = "节假日设置")
@RequestMapping("/v1/HolidayVacations")
public class HolidayVacationsController {
    @Resource
    private IHolidayVacationsService iHolidayVacationsService;

    /**
     * 获取流程关键字信息列表
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "获取节假日信息")
    @PostMapping("/getHolidayVacations")
    public BaseResponse getHolidayVacations(@RequestBody HolidayVacations request) {
        log.info("getHolidayVacations");
        return iHolidayVacationsService.getHolidayVacations(request);
    }

    @ApiOperation(value = "获取*个工作日之前的日期")
    @GetMapping("/getBeforeWorkDay")
    public BaseResponse getBeforeWorkDay(@RequestParam Integer day) {
        log.info("getBeforeWorkDay");
        return iHolidayVacationsService.getBeforeWorkDay(day);
    }

    /**
     * 新增或修改流程关键字信息
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "批量新增节或修改假日信息")
    @PostMapping("/createHolidayVacations")
    public BaseResponse createHolidayVacations(@RequestBody List<HolidayVacations> request) {
        log.info("createHolidayVacations");
        return iHolidayVacationsService.createHolidayVacations(request);
    }

    /**
     * 新增或修改流程关键字信息
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "单条新增或修改节假日信息")
    @PostMapping("/createHolidayVacationsOne")
    public BaseResponse createHolidayVacationsOne(@RequestBody HolidayVacations request) {
        log.info("createHolidayVacationsOne");
        return iHolidayVacationsService.createHolidayVacationsOne(request);
    }


    @ApiOperation(value = "查询工作日天数(s:start开始时间 e:end结束时间,格式yyyy-MM-dd)")
    @GetMapping("/getWorkDayBetween")
    public BaseResponse getWorkDayBetween(@RequestParam String s, @RequestParam String e) {
        log.info("getWorkDayBetween");
        return iHolidayVacationsService.getWorkDayBetween(s, e);
    }

}
