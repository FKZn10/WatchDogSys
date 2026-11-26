package com.jpmsoft.projectProducer.pojo.vo.integrity;

import lombok.Data;

import java.util.Map;

@Data
public class IntegrityWarningPositionVo {
    /**
     * 预警指标
     */
    private String wriIndicator;
    /**
     * 预警提示等级
     */
    private String wriLevel;
    /**
     * 岗位名称
     */
    private String positionName;
    /**
     * 岗位编码
     */
    private String positionCode;

    /**
     * 模块名称
     */
    private String modelCode;

    private Map<String, String> modelCodes;

    /**
     * 模块编码
     */
    private String businessCode;

    /**
     * 预警次数
     */
    private Integer count;
}
