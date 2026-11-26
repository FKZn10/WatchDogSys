package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecInvitationSchemaDTO;
import com.jpmsoft.projectProducer.service.secInfo.ISecInvitationSchemaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 邀请招标
 */
@Slf4j
@RestController
@RequestMapping("/v1/secInvitationSchema")
public class SecInvitationSchemaController {
    @Resource
    private ISecInvitationSchemaService secInvitationSchemaService;

    /**
     * 获取邀请招标列表
     *
     * @param request
     * @return
     */
    @PostMapping("/getSecInvitationSchemaList")
    public BaseResponse getSecInvitationSchemaList(@RequestBody SecInvitationSchemaDTO request) {
        log.info("getSecInvitationSchemaList");
        return secInvitationSchemaService.getSecInvitationSchemaList(request);
    }

    /**
     * 获取邀请招标多条件筛选
     *
     * @param request
     * @return
     */
    @PostMapping("/getSecInvitationSchemaClassInfo")
    public BaseResponse getSecInvitationSchemaClassInfo(@RequestBody SecInvitationSchemaDTO request) {
        log.info("getSecInvitationSchemaClassInfo");
        return secInvitationSchemaService.getSecInvitationSchemaClassInfo(request);
    }
}
