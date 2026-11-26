package com.jpmsoft.jputils.dto;

import lombok.Data;

/**
 * 华润短信入参 条
 *
 * @Author: 李文涛 aaron
 * @date: 2022/12/16 14:35
 */
@Data
public class CrcSmsDto extends AliyunSmsDto {
    /**
     * 请求地址
     */
    private String url;
    /**
     * 租户ID
     */
    private String tenantId;
    /**
     * 提交人ID
     */
    private String submiter;
}
