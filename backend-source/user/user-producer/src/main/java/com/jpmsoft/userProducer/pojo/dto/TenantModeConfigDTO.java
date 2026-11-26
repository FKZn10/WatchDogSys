package com.jpmsoft.userProducer.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 租户模式配置信息
 *
 * @Author: 李文涛 aaron
 * @date: 2023/6/26 8:54
 */
@Data
public class TenantModeConfigDTO implements Serializable {

    private static final long serialVersionUID = -8224545038910831327L;

    /**
     * 是否启用租户模式
     */
    private boolean enable;

}
