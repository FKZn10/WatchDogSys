package com.jpmsoft.common.model.requestAndResponse.SecInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: 李文涛 aaron
 * @date: 2022/10/25 15:09
 */
@Data
public class SecBiddingSchemeUnitDto implements Serializable {

    private static final long serialVersionUID = -2235416871128283019L;
    private String id;
    private String bsId;
    @JsonProperty("cName")
    private String cName;
    private String bsuQualifications;
    private BigDecimal bsuOffer;
    private String bsuPerformance;
    private String bsuOther;
    private String bsuFile;
    private String bsuFileIds;
}
