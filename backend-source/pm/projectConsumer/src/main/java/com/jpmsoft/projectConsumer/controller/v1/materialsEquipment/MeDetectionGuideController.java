package com.jpmsoft.projectConsumer.controller.v1.materialsEquipment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.MeEnter.MeDetectionGuideModel;
import com.jpmsoft.projectConsumer.feign.MaterialFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "检测指引", value = "检测指引")
@RequestMapping("/material/v1/meDetectionGuide")
public class MeDetectionGuideController {
    @Autowired
    private MaterialFeign materialFeign;


    @ApiOperation(value = "新增或修改检测指引")
    @PostMapping("/createOrUpdateMeDetectionGuide")
    public BaseResponse createOrUpdateMeDetectionGuide(@RequestBody MeDetectionGuideModel model) {
        log.info("createOrUpdateMeDetectionGuide");
        return materialFeign.createOrUpdateMeDetectionGuide(model);
    }

    @ApiOperation(value = "分页查检测指引")
    @PostMapping("/getMeDetectionGuideList")
    public BaseResponse getMeDetectionGuideList(@RequestBody MeDetectionGuideModel model, @RequestHeader String token) {
        log.info("getMeDetectionGuideList");
        return materialFeign.getMeDetectionGuideList(model, token);
    }

    @ApiOperation(value = "ID查检测指引")
    @GetMapping("/getMeDetectionGuideByDgId")
    public BaseResponse getMeDetectionGuideByDgId(@RequestParam String dgId) {
        log.info("getMeDetectionGuideByDgId");
        return materialFeign.getMeDetectionGuideByDgId(dgId);
    }

    @GetMapping("/approvalData")
    public BaseResponse approvalData(@RequestParam String dgId) {
        log.info("approvalData");
        return materialFeign.approvalData(dgId);
    }
    @GetMapping("/setMeDetectionGuideData")
    public BaseResponse setMeDetectionGuideData(@RequestParam String dgId) {
        log.info("setMeDetectionGuideData");
        return materialFeign.setMeDetectionGuideData(dgId);
    }
}
