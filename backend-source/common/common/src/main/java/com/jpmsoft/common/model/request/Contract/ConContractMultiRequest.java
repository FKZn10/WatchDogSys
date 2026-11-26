package com.jpmsoft.common.model.request.Contract;

import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 多项目监理合同
 *
 * @author tianhelin
 * @date 2025/9/8
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ConContractMultiRequest extends BaseFileRequest {
    private String id;
    private String createUid;
    private String createUname;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    private String name;
    private String code;
    private BigDecimal amount;
    private Date signingDate;
    private String clientUnitId;
    private String clientUnitName;
    private String clientSignatory;
    private String clientAgent;
    private String clientAgentTel;
    private String clientAddress;
    private String supervisionUnitId;
    private String supervisionUnitName;
    private String supervisionSignatory;
    private String supervisionAgent;
    private String supervisionAgentTel;
    private String supervisionAddress;
    private String depositBank;
    private String depositAccount;
    private String paymentType;
    private String conCategory;
    private String contractType;
    private String contractFile;
    private String contractFileIds;
    private Integer contractYear;
    private String biddingType;
    private String contractCurrency;
    private Boolean biddingPublic;
    private String contractExecution;
    private String contractStatus;
    private String projectLeader;
    private String contractMainFile;
    private String contractMainFileIds;
    private Boolean contractHistory;
    private Date planStartTime;
    private Date planEndTime;

    private List<ConContractMultiProjectRequest> conContractMultiProject;
    private List<ConContractMultiFundingRequest> conContractMultiFunding;
}
