package com.jpmsoft.common.model.response.SecInfo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SecInfoSubjectChangeResponse {
    private String id;
    private String sicId;
    private String secId;
    /**
     * 估算科目id
     */
    private String subIebId;
    /**
     * 概算科目id
     */
    private String subIbbId;
    private BigDecimal subMoney;

    /**
     * 科目的名称
     */
    private String subName;

    private String levelCode;
}