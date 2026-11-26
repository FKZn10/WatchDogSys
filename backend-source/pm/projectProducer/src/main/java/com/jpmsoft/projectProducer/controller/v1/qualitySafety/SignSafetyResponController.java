package com.jpmsoft.projectProducer.controller.v1.qualitySafety;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SafeResponsibility.SignSafetyResponDTO;
import com.jpmsoft.projectProducer.service.qualitySafety.ISignSafetyResponService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 安全责任书签订
 */
@Slf4j
@RestController
@RequestMapping("/v1/signSafetyRespon")
public class SignSafetyResponController {

    @Resource
    private ISignSafetyResponService signSafetyResponService;

    /**
     * 根据人员申报表发起安全责任书签订并发送待办
     *
     * @param rpId 人员申报表主键
     * @return 安全责任书主键
     */
    @GetMapping("/createSignSafetyResponByReported")
    public BaseResponse createSignSafetyResponByReported(@RequestParam String rpId, @RequestHeader String token) {
        log.info("createSignSafetyResponByReported rpId:{}", rpId);
        return signSafetyResponService.createSignSafetyResponByReported(rpId, token);
    }

    /**
     * 获取安全责任书签订列表
     *
     * @param request 筛选条件
     * @return 安全责任书签订列表
     */
    @PostMapping("/getSignSafetyResponList")
    public BaseResponse getSignSafetyResponList(@RequestBody SignSafetyResponDTO request) {
        log.info("getSignSafetyResponList");
        return signSafetyResponService.getSignSafetyResponList(request);
    }

    /**
     * 获取安全责任书签订详情
     *
     * @param id 主键
     * @return 安全责任书签订详情
     */
    @GetMapping("/getSignSafetyResponById")
    public BaseResponse getSignSafetyResponById(@RequestParam String id) {
        log.info("getSignSafetyResponById id:{}", id);
        return signSafetyResponService.getSignSafetyResponById(id);
    }
}
