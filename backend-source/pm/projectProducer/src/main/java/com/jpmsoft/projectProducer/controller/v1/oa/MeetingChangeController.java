package com.jpmsoft.projectProducer.controller.v1.oa;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.service.oa.IChangeMeetingNotificationService;
import com.jpmsoft.projectProducer.service.oa.IChangeTimeoutUnlockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/10/29 14:40
 */
@Slf4j
@RestController
@RequestMapping("/v1/OA/meetingChange")
public class MeetingChangeController {
    @Resource
    private IChangeMeetingNotificationService changeMeetingNotificationService;
    @Resource
    private IChangeTimeoutUnlockService changeTimeoutUnlockService;

    @GetMapping("/timeOut2LockMeeting")
    public BaseResponse timeOut2LockMeeting(@RequestParam Integer day) {
        log.info("timeOut2LockMeeting");
        return changeMeetingNotificationService.timeOut2LockMeeting(day);
    }

    @GetMapping("/approve2UnlockMeeting")
    public BaseResponse approve2UnlockMeeting(@RequestParam String id) {
        log.info("approve2UnlockMeeting");
        return changeTimeoutUnlockService.approve2UnlockMeeting(id);
    }


    @GetMapping("/queryDataList")
    public BaseResponse  queryDataList(@RequestParam Integer page,@RequestParam Integer size){
        return changeMeetingNotificationService.queryDataList(page,size);
    }




}
