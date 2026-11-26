package com.jpmsoft.projectProducer.controller.v1.design;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Design.DesignQrCodeRequest;
import com.jpmsoft.common.model.request.Design.DesignResultsIssuedRequest;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignQrCodeModel;
import com.jpmsoft.projectProducer.service.design.IDesignQrCodeService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/designQrCode")
public class DesignQrCodeController {

    @Resource
    private IDesignQrCodeService designQrCodeService;

    @Resource
    private RedisUtils redisUtils;

    @PostMapping("/createOrupdateDesignQrCode")
    public BaseResponse createOrupdateDesignQrCode(@RequestBody DesignQrCodeModel request, @RequestHeader String token) {
        return designQrCodeService.createOrupdateDesignQrCode(request, token);
    }

    @GetMapping("/getDesignQrCodeById")
    public BaseResponse getDesignQrCodeById(@RequestParam String id, @RequestHeader String token) {
        return BaseResponse.ok(designQrCodeService.getDesignQrCodeById(id, token));
    }

    @GetMapping("/getDeleteDesignQrCodeById")
    public BaseResponse deleteDesignQrCodeById(@RequestParam String id, @RequestHeader String token) {
        return designQrCodeService.deleteDesignQrCodeById(id);
    }

    @PostMapping("/checkDqcuserd")
    public BaseResponse checkDqcuserd(@RequestBody DesignQrCodeModel request, @RequestHeader String token) {
        return designQrCodeService.checkDqcuserd(request, token);
    }


    /**
     * 获取列表
     *
     * @param request
     * @param token
     * @return
     */
    @PostMapping("/getDesignQrCodeList")
    public BaseResponse getDesignQrCodeList(@RequestBody DesignQrCodeRequest request, @RequestHeader String token) {
        log.info("getDesignQrCodeList");
        return designQrCodeService.getDesignQrCodeList(request, redisUtils.getUid(token));
    }

    /**
     * 查询信息
     *
     * @param request
     * @param token
     * @return
     */
    @PostMapping("/getDesignQrCodeClassInfo")
    public BaseResponse getDesignQrCodeClassInfo(@RequestBody DesignQrCodeRequest request, @RequestHeader String token) {
        log.info("getDesignQrCodeClassInfo");
        return designQrCodeService.getDesignQrCodeClassInfo(request,redisUtils.getUid(token));
    }

    @GetMapping("/getDesignQrCodeDateById")
    public BaseResponse getDesignQrCodeDateById(@RequestParam String id, @RequestHeader String token) {
        log.info("getDesignQrCodeDateById");
        return designQrCodeService.getDesignQrCodeDateById(id,redisUtils.getUid(token));
    }

}
