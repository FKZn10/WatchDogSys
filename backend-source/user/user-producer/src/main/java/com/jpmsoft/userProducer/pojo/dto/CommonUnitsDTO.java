package com.jpmsoft.userProducer.pojo.dto;

import lombok.Data;

/**
 * 配置项CommonUnits
 */
@Data
public class CommonUnitsDTO {
    /**
     * 单位id
     */
    private String cId;

    /**
     * 排序号
     */
    private Integer orderNumber;

    /**
     * 所属模块
     */
    private String module;
}
