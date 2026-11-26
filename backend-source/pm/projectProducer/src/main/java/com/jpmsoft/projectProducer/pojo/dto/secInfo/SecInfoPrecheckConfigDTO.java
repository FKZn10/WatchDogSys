package com.jpmsoft.projectProducer.pojo.dto.secInfo;

import lombok.Data;

import java.util.List;

@Data
public class SecInfoPrecheckConfigDTO {
    /**
     * 当前tCode
     */
    private String currentTCode;

    /**
     * 前置
     */
    private List<String> precheckTCodeList;

    /**
     * 招标方式列表
     */
    private List<String> secBiddingTypes;
}
