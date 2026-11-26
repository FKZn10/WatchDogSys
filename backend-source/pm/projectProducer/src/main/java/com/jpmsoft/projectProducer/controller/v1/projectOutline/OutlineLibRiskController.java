package com.jpmsoft.projectProducer.controller.v1.projectOutline;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.projectOutline.OutlineLibRiskRequest;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.service.projectOutline.IOutlineLibRiskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 体系模板管理风险指标
 */
@Slf4j
@RestController
@RequestMapping("/v1/projectOutline/libRisk")
public class OutlineLibRiskController {
    @Resource
    private IOutlineLibRiskService outlineLibRiskService;

    @PostMapping("/createOutlineLibRisk")
    public BaseResponse createOutlineLibRisk(@RequestBody OutlineLibRiskRequest request, @RequestHeader String token) {
        log.info("createOutlineLibRisk");
        return outlineLibRiskService.createOutlineLibRisk(request, token);
    }

    @GetMapping("/getOutlineLibRiskByOslId")
    public BaseResponse getOutlineLibRiskByOslId(@RequestParam String oslId) {
        log.info("getOutlineLibRiskByOslId");
        return BaseResponse.ok(outlineLibRiskService.getOutlineLibRiskByOslId(oslId));
    }

    @GetMapping("/getOutlineLibRiskById")
    public BaseResponse getOutlineLibRiskById(@RequestParam String id) {
        log.info("getOutlineLibRiskById");
        return outlineLibRiskService.getOutlineLibRiskById(id);
    }

    @PostMapping("/getOutlineLibRiskPage")
    public BaseResponse getOutlineLibRiskPage(@RequestBody OutlineLibRiskRequest request) {
        log.info("getOutlineLibRiskPage");
        return outlineLibRiskService.getOutlineLibRiskPage(request);
    }

    @GetMapping("/deleteOutlineLibRiskById")
    public BaseResponse deleteOutlineLibRiskById(@RequestParam String id, @RequestHeader String token) {
        log.info("deleteOutlineLibRiskById");
        return outlineLibRiskService.deleteOutlineLibRiskById(id, token);
    }

    @PostMapping("/updateOutlineLibRisk")
    public BaseResponse updateOutlineLibRisk(@RequestBody OutlineLibRiskRequest request, @RequestHeader String token) {
        log.info("updateOutlineLibRisk");
        return outlineLibRiskService.updateOutlineLibRisk(request, token);
    }

    @PostMapping("/autoSaveOutlineLibRisk")
    public BaseResponse autoSaveOutlineLibRisk(@RequestBody OutlineLibRiskRequest request, @RequestHeader String token) {
        log.info("autoSaveOutlineLibRisk");
        if (ValidateUtil.isEmpty(request.getId())) {
            return outlineLibRiskService.createOutlineLibRisk(request, token);
        } else {
            return outlineLibRiskService.updateOutlineLibRisk(request, token);
        }
    }
}
