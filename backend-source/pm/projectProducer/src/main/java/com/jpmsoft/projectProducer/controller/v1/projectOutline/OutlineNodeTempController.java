package com.jpmsoft.projectProducer.controller.v1.projectOutline;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.projectOutline.OutlineNodeTempRequest;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.service.projectOutline.IOutlineNodeTempService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/projectOutline/nodeTemp")
public class OutlineNodeTempController {
    @Resource
    private IOutlineNodeTempService outlineNodeTempService;

    @PostMapping("/createOutlineNodeTemp")
    public BaseResponse createOutlineNodeTemp(@RequestBody OutlineNodeTempRequest request, @RequestHeader String token) {
        log.info("createOutlineNodeTemp");
        return outlineNodeTempService.createOutlineNodeTemp(request, token);
    }

    @PostMapping("/getOutlineNodeTempList")
    public BaseResponse getOutlineNodeTempList(@RequestBody OutlineNodeTempRequest request) {
        log.info("getOutlineNodeTempList");
        return outlineNodeTempService.getOutlineNodeTempList(request);
    }

    @GetMapping("/deleteOutlineNodeTempById")
    public BaseResponse deleteOutlineNodeTempById(@RequestParam String id, @RequestHeader String token) {
        log.info("deleteOutlineNodeTempById");
        return outlineNodeTempService.deleteOutlineNodeTempById(id, token);
    }

    @PostMapping("/updateOutlineNodeTemp")
    public BaseResponse updateOutlineNodeTemp(@RequestBody OutlineNodeTempRequest request, @RequestHeader String token) {
        log.info("updateOutlineNodeTemp");
        return outlineNodeTempService.updateOutlineNodeTemp(request, token);
    }

    @PostMapping("/autoSaveOutlineNodeTemp")
    public BaseResponse autoSaveOutlineNodeTemp(@RequestBody OutlineNodeTempRequest request, @RequestHeader String token) {
        log.info("autoSaveOutlineNodeTemp");
        if (ValidateUtil.isEmpty(request.getId())) {
            return outlineNodeTempService.createOutlineNodeTemp(request, token);
        } else {
            return outlineNodeTempService.updateOutlineNodeTemp(request, token);
        }
    }
}
