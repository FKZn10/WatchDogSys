package com.jpmsoft.projectProducer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 多项目合同
 *
 * @author tianhelin
 * @date 2025/9/8
 */
@Data
@TableName("Con_Contract_Multi")
public class ConContractMulti {
    @TableId(type = IdType.INPUT)
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
    private String clientSignatory;
    private String clientAgent;
    private String clientAgentTel;
    private String clientAddress;
    private String supervisionUnitId;
    private String supervisionSignatory;
    private String supervisionAgent;
    private String supervisionAgentTel;
    private String supervisionAddress;
    private String depositBank;
    private String depositAccount;
    private String paymentType;
    private String contractType;
    private String conCategory;
    private String contractFile;
    private Integer contractYear;
    private String biddingType;
    private String contractCurrency;
    private Boolean biddingPublic;
    private String contractExecution;
    private String contractStatus;
    private String projectLeader;
    private String contractMainFile;
    private Boolean contractHistory;
    private Date planStartTime;
    private Date planEndTime;

    /**
     * 采购项目（标段）ID。
     */
    private String conSectionId;

    /**
     * 发包依据。
     */
    private String conBasis;

    /**
     * 发包依据附件关联ID。
     */
    private String conBasisFile;

    /**
     * 合同计价方式。
     */
    private String conPricingMethod;

    /**
     * 其他计价说明。
     */
    private String conOtherValuation;

    /**
     * 实际开工日期。
     */
    private Date conStartDate;

    /**
     * 开工令附件关联ID。
     */
    private String conStartsTo;

    /**
     * 预付款比例。
     */
    private Float conAcPayRate;

    /**
     * 预付款金额。
     */
    private BigDecimal conAcMoney;

    /**
     * 是否补充合同。
     */
    private String conSup;

    /**
     * 主合同ID。
     */
    private String conMasterConId;

    /**
     * 是否需要更改金额。
     */
    private String conSupIsChangeMoney;

    /**
     * 金额调整类型。
     */
    private Integer conSupChangeMonType;

    /**
     * 更改的金额。
     */
    private BigDecimal conSupChangeMoney;

    /**
     * 是否需要更改工期。
     */
    private String conSupIsChangeDuration;

    /**
     * 工期调整类型。
     */
    private Integer conSupChangeDurType;

    /**
     * 更改的工期（日历天）。
     */
    private Integer conSupChangeDuration;

    /**
     * 是否联合体合同。
     */
    private String conConsortium;

    /**
     * 是否多方主体合同。
     */
    private String conMultiSubject;

    /**
     * 其他乙方1单位ID。
     */
    private String conPartyB1;

    /**
     * 其他乙方2单位ID。
     */
    private String conPartyB2;

    /**
     * 其他乙方3单位ID。
     */
    private String conPartyB3;

    /**
     * 其他乙方4单位ID。
     */
    private String conPartyB4;

    /**
     * 丙方单位ID。
     */
    private String conPartyCId;

    /**
     * 丁方单位ID。
     */
    private String conPartyDId;

    /**
     * 是否需要法制办审核。
     */
    private String htjkzffs;

    /**
     * 是否需要基建管理科审核。
     */
    private String conjgl;

    /**
     * 合同内容。
     */
    private String conContent;
}