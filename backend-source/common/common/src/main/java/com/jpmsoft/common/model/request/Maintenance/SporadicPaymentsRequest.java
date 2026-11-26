package com.jpmsoft.common.model.request.Maintenance;

import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Data
public class SporadicPaymentsRequest extends BaseFileRequest {

    private String id;

    private String cretateUid;

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
    private List<String> spPFillindata;

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
    private List<Date> spPPaymentdata;

    /**
     * 区域负责人id
     */
    private String spPAreamanagerUid;

    /**
     * 区域负责人名称
     */
    private String spPAreamanagerUname;

    private Date spPPayDate;

    private String mrCategory;
}
