package com.jpmsoft.common.model.request.SecInfo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SecInfoSubjectChangeRequest {
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

    private String levelCode;
}
