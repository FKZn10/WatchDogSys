package com.jpmsoft.projectProducer.pojo.vo.integrity;

import lombok.Data;

import java.util.List;

@Data
public class IntegrityWarningVo {
    /**
     * 模块编码
     */
    private String modelCode;
    /**
     * 模块名称
     */
    private String modelName;

    /**
     * 颜色
     */
    private String color;

    /**
     * 具体内容
     */
    private List<IntegrityWarningChildVo> data;
}
