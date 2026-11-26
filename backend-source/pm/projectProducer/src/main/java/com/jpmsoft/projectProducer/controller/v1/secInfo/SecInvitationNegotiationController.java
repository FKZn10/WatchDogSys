package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecInvitationNegotiationDTO;
import com.jpmsoft.projectProducer.service.secInfo.ISecInvitationNegotiationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 邀标控制价
 */
@Slf4j
@RestController
@RequestMapping("/v1/secInvitationNegotiation")
public class SecInvitationNegotiationController {
    @Resource
    private ISecInvitationNegotiationService secInvitationNegotiationService;

    /**
     * 分页查询邀标控制价
     *
     * @param request
     * @return
     */
    @PostMapping("/getSecInvitationNegotiationList")
    public BaseResponse getSecInvitationNegotiationList(@RequestBody SecInvitationNegotiationDTO request) {
        log.info("getSecInvitationNegotiationList");
        return secInvitationNegotiationService.getSecInvitationNegotiationList(request);
    }

    /**
     * 获取邀标控制价多条件筛选信息
     *
     * @param request
     * @return
     */
    @PostMapping("/getSecInvitationNegotiationClassInfo")
    public BaseResponse getSecInvitationNegotiationClassInfo(@RequestBody SecInvitationNegotiationDTO request) {
        log.info("getSecInvitationNegotiationClassInfo");
        return secInvitationNegotiationService.getSecInvitationNegotiationClassInfo(request);
    }
}
