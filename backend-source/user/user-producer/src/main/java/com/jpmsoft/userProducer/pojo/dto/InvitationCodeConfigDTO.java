package com.jpmsoft.userProducer.pojo.dto;

import lombok.Data;

/**
 * sysconfig 中 invitationCodeConfig 的 JSON 配置。
 */
@Data
public class InvitationCodeConfigDTO {
    /**
     * 每个用户可持有的未过期邀请码数量上限，必须为正整数。
     */
    private Integer maxUnexpiredCount;

    /**
     * 邀请码有效天数，从生成时间开始计算，必须为正整数。
     */
    private Integer expireDays;
}
