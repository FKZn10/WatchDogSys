package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecInvitationSchemaDTO;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "邀请招标", value = "邀请招标")
@RequestMapping("/v1/secInvitationSchema")
public class SecInvitationSchemaController {
    @Resource
    private SecFeign secFeign;

    /**
     * 获取邀请招标列表
     *
     * @param request
     * @return
     */
    @ApiOperation("获取邀请招标列表")
    @PostMapping("/getSecInvitationSchemaList")
    public BaseResponse getSecInvitationSchemaList(@RequestBody SecInvitationSchemaDTO request) {
        log.info("getSecInvitationSchemaList");
        return secFeign.getSecInvitationSchemaList(request);
    }

    /**
     * 获取邀请招标多条件筛选
     *
     * @param request
     * @return
     */
    @ApiOperation("获取邀请招标多条件筛选")
    @PostMapping("/getSecInvitationSchemaClassInfo")
    public BaseResponse getSecInvitationSchemaClassInfo(@RequestBody SecInvitationSchemaDTO request) {
        log.info("getSecInvitationSchemaClassInfo");
        return secFeign.getSecInvitationSchemaClassInfo(request);
    }
}
