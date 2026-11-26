package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysInvitationCodeDTO;
import com.jpmsoft.userProducer.pojo.entity.SysInvitationCode;

/**
 * 邀请码
 */
public interface ISysInvitationCodeService extends IService<SysInvitationCode> {
    /**
     * 为当前登录用户在指定项目下生成邀请码，按 invitationCodeConfig 限制用户总数量和有效期。
     *
     * @param pId 关联项目 ID，必填
     * @return 新生成的邀请码 DTO 或失败提示
     */
    BaseResponse generateInvitationCode(String pId);

    /**
     * 按可选项目 ID、项目名称及其他条件分页查询当前用户的邀请码，返回项目 ID、名称和过期标识。
     *
     * @param request 筛选条件及分页参数，pId 精确匹配、pName 模糊匹配，均可不传；创建人固定为当前用户
     * @return 包含当前页邀请码 DTO 列表及筛选结果总条数的响应
     */
    BaseResponse getInvitationCodeList(SysInvitationCodeDTO request);
}
