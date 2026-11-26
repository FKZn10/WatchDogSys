package com.jpmsoft.projectProducer.pojo.vo.integrity;

import lombok.Data;

@Data
public class IntegrityWarningChildVo {
    /**
     * 预警指标
     */
    private String wriIndicator;
    /**
     * 预警提示等级
     */
    private String wriLevel;
    /**
     * 数量
     */
    private String count;
    /**
     * 模板编码
     */
    private String businessCode;
    /**
     * 模块编码
     */
    private String modelCode;
}
