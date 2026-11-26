package com.jpmsoft.common.model.requestAndResponse.SecInfo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SecOpenResultCandidateModel {
    private String orcId;
    //开评标结果表id
    private String orId;

    //候选人单位id
    private String orcCandidateId;

    //候选人单位名称
    private String orcCandidateName;

    //候选人报价/费率
    private BigDecimal orcQuote;

    //候选人组
    private Integer orcGroupNum;

    //组内排序
    private Integer orcSort;

}
