package com.jpmsoft.projectConsumer.controller.v1.oa;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.feign.OaFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/10/29 14:40
 */
@Slf4j
@RestController
@Api(tags = "义乌会议变更", value = "义乌会议变更")
@RequestMapping("/v1/OA/meetingChange")
public class MeetingChangeController {
    @Resource
    private OaFeign oaFeign;

    @ApiOperation(value = "超时锁定会议入参超时天数，用于定时任务")
    @GetMapping("/timeOut2LockMeeting")
    public BaseResponse timeOut2LockMeeting(@RequestParam Integer day) {
        log.info("timeOut2LockMeeting");
        return oaFeign.timeOut2LockMeeting(day);
    }

    @ApiOperation(value = "会议解锁回调接口 审批通过解锁会议")
    @GetMapping("/approve2UnlockMeeting")
    public BaseResponse approve2UnlockMeeting(@RequestParam String id) {
        log.info("approve2UnlockMeeting");
        return oaFeign.approve2UnlockMeeting(id);
    }

    @ApiOperation(value = "会议查询")
    @GetMapping("/queryDataList")
    public BaseResponse  queryDataList(@RequestParam Integer page,@RequestParam Integer size){
        log.info("queryDataList");
        return oaFeign.queryDataList(page,size);
    }
}
