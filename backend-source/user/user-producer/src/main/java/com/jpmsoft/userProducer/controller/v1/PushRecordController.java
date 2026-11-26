package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.PushData.PushRecordModel;
import com.jpmsoft.userProducer.service.IPushRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@Api(tags = "数据推送记录", value = "数据推送记录")
@RequestMapping("/v1/dataPush")
public class PushRecordController {

    @Resource
    private IPushRecordService pushRecordService;

    @ApiOperation(value = "数据推送记录列表")
    @PostMapping("/getPushRecordList")
    public BaseResponse getPushRecordList(@RequestBody PushRecordModel request) {
        log.info("getPushRecordList");
        return pushRecordService.getPushRecordList(request);
    }
}
