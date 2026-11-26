package com.jpmsoft.common.model.requestAndResponse.JpmOa.contract;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class UnpredictableFeeDetailModel {
    /**
     *
     */
    private String id;

    /**
     * 客户服务费id
     */
    private String unpredictableFeeId;


    private String contractId;
    private String contractName;
    /**
     * 金额
     */

    private BigDecimal amount;
    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人id
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
     * 状态
     */
    private Integer status;

    /**
     * 删除标志
     */
    private Integer delFlag;
}
