package com.jpmsoft.projectProducer.pojo.dto.investment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 结算二审
 *
 * @author: 李文涛 aaron
 * @date: 2022/10/21 9:57
 */
@Data
public class InvSettlementSecondDto extends BaseFileDto {
    private static final long serialVersionUID = -6320354675760300657L;
    private String saId;
    @JsonProperty("pId")
    private String pId;
    private String conId;
    private Boolean ssHistory;
    private BigDecimal ssApprovedAmt;
    private String ssApprovedCom;
    private String ssApprovedFile;

    @JsonProperty("pName")
    private String pName;
    private String conName;
    private String ssApprovedFileIds;
    private String conType;
    private String saContractorName;
    private String saSupervisorName;
    private String saCostConsultName;
    private String ssApprovedComName;
    private Boolean saHistorical;
}
