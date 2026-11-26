package com.jpmsoft.common.model.response.Maintenance;

import com.jpmsoft.common.model.BaseFileResponse;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class SporadicPaymentsResponse extends BaseFileResponse {

    private String id;

    private String createUid;

    private String createUname;

    private Date createTime;

    private Integer state;

    private Date stateTime;

    /**
     * 工程名称
     */
    private String spPName;

    /**
     * 零星维修支付流水号
     */
    private String spPNumber;

    /**
     * 付款金额（元）
     */
    private BigDecimal spPPaymentamount;

    /**
     * 填报日期
     */
    private Date spPFillindata;

    /**
     * 申请单位id
     */
    private String spPApplyunitid;

    /**
     * 申请单位名称
     */
    private String spPApplyunitname;

    /**
     * 支付时间
     */
    private Date spPPaymentdata;

    /**
     * 区域负责人id
     */
    private String spPAreamanagerUid;

    /**
     * 区域负责人名称
     */
    private String spPAreamanagerUname;

    private String mfSource;

    private String mfCode;

    private Date spPPayDate;


    private List<SporadicPaymentsDtlResponse> sporadicPaymentsDtl;
}
