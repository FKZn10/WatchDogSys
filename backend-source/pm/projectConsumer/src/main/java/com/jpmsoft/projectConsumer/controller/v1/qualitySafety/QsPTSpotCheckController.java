package com.jpmsoft.projectConsumer.controller.v1.qualitySafety;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.qualitySafety.QsPTSpotCheckDto;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/safety/v1/qsPTSpotCheck")
public class QsPTSpotCheckController {

    @Resource
    private BaseFeign baseFeign;

    /**
     * 新增修改质量安全抽查
     *
     * @param request
     * @return
     */
    @PostMapping("/createOrUpdateQsPTSpotCheck")
    public BaseResponse createOrUpdateQsPTSpotCheck(@RequestBody QsPTSpotCheckDto request) {
        log.info("createOrUpdateQsPTSpotCheck");
        return baseFeign.createOrUpdateQsPTSpotCheck(request);
    }

    @PostMapping("/getQsPTSpotCheckList")
    public BaseResponse getQsPTSpotCheckList(@RequestBody QsPTSpotCheckDto request, @RequestHeader String token) {
        log.info("getQsPTSpotCheckList");
        return baseFeign.getQsPTSpotCheckList(request, token);
    }

    @PostMapping("/getQsPTSpotCheckListNoAuth")
    public BaseResponse getQsPTSpotCheckListNoAuth(@RequestBody QsPTSpotCheckDto request) {
        log.info("getQsPTSpotCheckListNoAuth");
        return baseFeign.getQsPTSpotCheckListNoAuth(request);
    }

    @GetMapping("/getQsPTSpotCheckById")
    public BaseResponse getQsPTSpotCheckById(@RequestParam String id) {
        log.info("getQsPTSpotCheckById");
        return baseFeign.getQsPTSpotCheckById(id);
    }

    @PostMapping("/approvalSpotCheckData")
    public BaseResponse approvalSpotCheckData(@RequestBody QsPTSpotCheckDto request) {
        log.info("approvalSpotCheckData");
        return baseFeign.approvalSpotCheckData(request);
    }

}
