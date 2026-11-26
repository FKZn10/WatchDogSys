package com.jpmsoft.projectProducer.pojo.vo.integrity;

import lombok.Data;

import java.util.List;

@Data
public class WarningSettingProgressJson {
    private Integer isInterceptFlow;
    private Integer isInterceptForm;
    private List<Object> warningRules;
    private String moduleColor;
}
