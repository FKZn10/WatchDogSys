package com.jpmsoft.common.model.requestAndResponse.SecInfo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 抽取专家信息（建管）
 *
 */
@Data
public class JGExpertModel implements Serializable {

    private static final long serialVersionUID = -3171669600101179527L;
    /**
     * 技术类人数
     */
    private int esTechnologyNum;
    /**
     * 经济类人数
     */
    private int esEconomicsNum;

    /**
     * 技术类专业列表
     */
    private List<String> exTechTypes;
    /**
     * 经济类专业列表
     */
    private List<String> exEconTypes;


}
