package com.jpmsoft.common.model.requestAndResponse.zdyxy;

import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class AnnualPlanModel extends BaseFileDto {
    private static final long serialVersionUID = -794592498480939484L;

    /**
     * 年
     */
    private Integer year;

    /**
     * 预算金额
     */
    private BigDecimal preAmount;

    /**
     * 支付金额
     */
    private BigDecimal payAmount;
    /**
     * 施工单位
     */
    private String constructionContractor;
    private String constructionContractorName;

    /**
     * 审计单位
     */
    private String auditUnit;
    private String auditUnitName;
    private String dispatchUid;
    private String dispatchUname;

    private String conName;
    private Date startTime;
    private Date endTime;
    /**
     * 生效日期 传入则筛选符合生效日期时间的数据
     */
    private Date effectiveDate;
}