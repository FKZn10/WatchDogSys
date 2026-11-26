package com.jpmsoft.projectProducer.pojo.vo.investment;

import com.jpmsoft.common.model.ProjectCommonRes;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 结算二审
 *
 * @author: 李文涛 aaron
 * @date: 2022/10/21 9:57
 */
@Data
public class InvSettlementSecondVo extends ProjectCommonRes implements Serializable {

    private static final long serialVersionUID = -3292984652687179739L;
    private String id;
    private String createUname;
    private String createUid;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    private String saId;
    private String conId;
    private Boolean ssHistory;
    private BigDecimal ssApprovedAmt;
    private String ssApprovedCom;
    private String ssApprovedFile;

    private String conName;
    private BigDecimal conMoneyTotal;
    private String conCode;
    private String conType;
    private String saContractorName;
    private String saSupervisorName;
    private String saCostConsultName;
    private String ssApprovedComName;
    private BigDecimal saContractorAmount;
    private BigDecimal saSupervisorAmount;
    private BigDecimal saCostConsultAmount;
    private BigDecimal saApprovedAmt;
    private Boolean saHistorical;
}
