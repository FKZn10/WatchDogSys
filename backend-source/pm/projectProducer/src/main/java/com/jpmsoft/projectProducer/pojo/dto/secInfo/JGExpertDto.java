package com.jpmsoft.projectProducer.pojo.dto.secInfo;

import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecExpertShieldDto;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 抽取专家信息（建管）
 *
 */
@Data
public class JGExpertDto implements Serializable {

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

    /**
     * 此字段是抽取的专家数量（
     * 城建招标代理专家抽取 内部专家不区分专业
     */
    private int extractNum;


}
