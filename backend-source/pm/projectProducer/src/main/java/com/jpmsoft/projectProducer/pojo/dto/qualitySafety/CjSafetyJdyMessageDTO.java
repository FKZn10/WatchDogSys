package com.jpmsoft.projectProducer.pojo.dto.qualitySafety;

import lombok.Data;

/**
 * 城建质量安全 超期临期给监督员发送平台消息系统配置
 */
@Data
public class CjSafetyJdyMessageDTO {
    /**
     * 监督员用户ID
     */
    private String jdyUid;

    /**
     * 安全问题是否发送临期消息
     */
    private Boolean safetyLqSend = false;
    /**
     * 安全问题是否发送逾期消息
     */
    private Boolean safetyYqSend = false;

    /**
     * 质量问题是否发送临期消息
     */
    private Boolean qualityLqSend = false;
    /**
     * 质量问题是否发送逾期消息
     */
    private Boolean qualityYqSen = false;
    /**
     * 安全问题临期消息标题
     */
    private String safetyLqTitle;
    /**
     * 安全问题预期消息标题
     */
    private String safetyYqTitle;
    /**
     * 安全问题临期消息内容
     */
    private String safetyLqMsg;
    /**
     * 安全问题预期消息内容
     */
    private String safetyYqMsg;
    /**
     * 质量问题临期消息标题
     */
    private String qualityLqTitle;
    /**
     * 质量问题预期消息标题
     */
    private String qualityYqTitle;
    /**
     * 质量问题临期消息标题
     */
    private String qualityLqMsg;
    /**
     * 质量问题预期消息标题
     */
    private String qualityYqMsg;

}
