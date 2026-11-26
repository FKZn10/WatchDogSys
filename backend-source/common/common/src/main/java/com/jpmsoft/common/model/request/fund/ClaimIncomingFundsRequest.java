package com.jpmsoft.common.model.request.fund;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseFileRequest;
import com.jpmsoft.common.model.requestAndResponse.fund.ClaimIncomingFundsDtlDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 来款认领 请求类
 *
 * @author tianhelin
 * @date 2025/11/17
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ClaimIncomingFundsRequest extends BaseFileRequest {
    /**
     * 主键ID
     */
    private String id;

    /**
     * 创建人ID
     */
    private String createUid;

    /**
     * 创建人名称
     */
    private String createUname;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 审批状态
     */
    private Integer state;

    /**
     * 审批时间
     */
    private Date stateTime;

    /**
     * 交易金额
     */
    private BigDecimal cifMoney;

    private List<String> cifMoneys;

    /**
     * 对方账号
     */
    private String cifAccount;

    /**
     * 对方账户
     */
    private String cifCa;

    /**
     * 交易时间
     */
    private Date cifDate;

    private List<String> cifDates;

    /**
     * 摘要
     */
    private String cifAbstract;

    /**
     * 用途
     */
    private String cifPurpose;

    /**
     * 附言
     */
    private String cifPostscript;

    private String pId;

    private String cifIsReceipt;

    private String cifIsWriteOff;

    private String cifReceipt;

    /**
     * 是否认领
     */
    private String cifIsIncoming;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新人ID
     */
    private String updateUserId;

    /**
     * 更新人名称
     */
    private String updateUserName;

    private String cifVerifyState;

    /**
     * 认领单位名称
     */
    @JsonProperty("cifCName")
    private String cifCName;

    /**
     * 认领单位Id
     */
    @JsonProperty("cifCId")
    private String cifCId;

    private String projectName;

    private String operationType;

    /**
     * 核销状态是否自动判断
     * 有值则不自动判断
     */
    private String verifyStateFlag;

    private List<ClaimIncomingFundsDtlDTO> dtlList;

    /**
     * 排除已使用
     */
    private String excludeUsed;
}
