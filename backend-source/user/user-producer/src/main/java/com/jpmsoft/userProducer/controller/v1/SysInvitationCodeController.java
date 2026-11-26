package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysInvitationCodeDTO;
import com.jpmsoft.userProducer.service.ISysInvitationCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 邀请码
 */
@Slf4j
@Api(tags = "邀请码", value = "邀请码")
@RestController
@RequestMapping("/v1/invitationCode")
public class SysInvitationCodeController {
    @Resource
    private ISysInvitationCodeService sysInvitationCodeService;

    /**
     * 为当前登录用户在指定项目下生成并保存邀请码，受系统配置的用户数量和有效期限制。
     *
     * @param pId 关联项目 ID，必填
     * @return 新生成的邀请码 DTO，或用户、配置、数量限制的失败提示
     */
    @ApiOperation(value = "生成邀请码")
    @GetMapping("/generateInvitationCode")
    public BaseResponse generateInvitationCode(@RequestParam("pId") String pId) {
        return sysInvitationCodeService.generateInvitationCode(pId);
    }

    /**
     * 按可选项目 ID、项目名称及其他条件分页查询当前用户的邀请码，返回项目 ID、名称和过期标识。
     *
     * @param request 筛选条件及分页参数，pId 精确匹配、pName 模糊匹配，均可不传；创建人固定为当前用户
     * @return 包含当前页邀请码 DTO 列表及筛选结果总条数的响应
     */
    @ApiOperation(value = "获取邀请码列表")
    @PostMapping("/getInvitationCodeList")
    public BaseResponse getInvitationCodeList(@RequestBody SysInvitationCodeDTO request) {
        log.info("getInvitationCodeList");
        return sysInvitationCodeService.getInvitationCodeList(request);
    }
}
