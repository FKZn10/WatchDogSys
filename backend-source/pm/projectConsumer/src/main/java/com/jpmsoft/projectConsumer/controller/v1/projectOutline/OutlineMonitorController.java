package com.jpmsoft.projectConsumer.controller.v1.projectOutline;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.feign.OutlineFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Slf4j
@RestController
@RequestMapping("/v1/outlineMonitor")
public class OutlineMonitorController {


    @Resource
    private OutlineFeign outlineFeign;

    @PostMapping("/insertMonitor")
    public BaseResponse insertMonitor(@RequestBody Object data) {
        return outlineFeign.insertMonitor(data);
    }

    @GetMapping("/getMonitor")
    public BaseResponse getMonitor(@RequestHeader String token) {
        return outlineFeign.getMonitor(token);
    }

}
