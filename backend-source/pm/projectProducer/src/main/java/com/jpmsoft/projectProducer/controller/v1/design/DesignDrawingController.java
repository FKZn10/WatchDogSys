package com.jpmsoft.projectProducer.controller.v1.design;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignDrawingModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.design.IDesignDrawingService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/designDrawing")
public class DesignDrawingController {

    @Resource
    private IDesignDrawingService designDrawingService;
    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateDesignDrawing")
    public BaseResponse createOrUpdateDesignDrawing(@RequestBody DesignDrawingModel request) {
        log.info("getDesignDrawingList");
        return designDrawingService.createOrUpdateDesignDrawing(request);
    }

    @TenantId
    @PostMapping("/getDesignDrawingList")
    public BaseResponse getDesignDrawingList(@RequestBody DesignDrawingModel request, @RequestHeader String token) {
        log.info("getDesignDrawingList");
        return designDrawingService.getDesignDrawingList(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getDesignDrawingListNoAuth")
    public BaseResponse getDesignDrawingListNoAuth(@RequestBody DesignDrawingModel request) {
        log.info("getDesignDrawingListNoAuth");
        return designDrawingService.getDesignDrawingListNoAuth(request);
    }

    @GetMapping("/getDesignDrawingById")
    public BaseResponse getDesignDrawingById(@RequestParam String ddId) {
        log.info("getDesignDrawingById");
        return designDrawingService.getDesignDrawingById(ddId);
    }

    @PostMapping("/getDesignDrawingListRW")
    public BaseResponse getDesignDrawingListRW(@RequestBody DesignDrawingModel request, @RequestHeader String token) {
        log.info("getDesignDrawingListRW");
        return designDrawingService.getDesignDrawingListRW(request, redisUtils.getUid(token));
    }

    @PostMapping("/getDesignDrawingExportRW")
    public List<DesignDrawingModel> getDesignDrawingExportRW(@RequestBody DesignDrawingModel request, @RequestHeader String token) {
        log.info("getDesignDrawingExportRW");
        return designDrawingService.getDesignDrawingExportRW(request, redisUtils.getUid(token));
    }
}
