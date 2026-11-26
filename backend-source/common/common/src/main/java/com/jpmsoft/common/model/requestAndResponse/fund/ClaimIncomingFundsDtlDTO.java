package com.jpmsoft.common.model.requestAndResponse.fund;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 资金管理-来款认领子表 DTO
 *
 * @author tianhelin
 * @date 2025-11-24
 */
@Data
public class ClaimIncomingFundsDtlDTO {
    /**
     * 主键
     */
    private String id;

    /**
     * 主表Id
     */
    private String cifId;

    /**
     * 项目Id
     */
    @JsonProperty("pId")
    private String pId;

    /**
     * 是否已开收据
     */
    private String cifdIsReceipt;

    /**
     * 收款Id
     */
    private String cifdReceipt;

    /**
     * 核销状态
     */
    private String cifdVerifyState;

    /**
     * 核销金额
     */
    private BigDecimal cifdAccount;

    /**
     * 集团已到位资金
     */
    private BigDecimal cifdGroupFunds;

    /**
     * 创建时间
     */
    private Date createTime;

    //=======主表字段========
    /**
     * 交易时间
     */
    private Date cifDate;

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

    private String cifCa;
}
