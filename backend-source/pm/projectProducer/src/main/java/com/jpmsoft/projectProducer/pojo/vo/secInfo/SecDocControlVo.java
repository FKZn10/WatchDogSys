package com.jpmsoft.projectProducer.pojo.vo.secInfo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.common.model.BaseFileResponse;
import com.jpmsoft.common.model.response.SecInfo.SecProjectResponse;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/4 11:13
 */
@Data
public class SecDocControlVo extends BaseFileResponse implements Serializable {

    private static final long serialVersionUID = 4248462552896447515L;

    private String id;
    private String secId;
    private String dcPreparedBy;
    private BigDecimal dcPreparedPrice;
    private BigDecimal dcConstructPrice;
    private BigDecimal dcEconomicIndicator;
    private BigDecimal dcNonCompetitive;
    private String dcBoqDoc;
    private String dcConsultUnitDoc;
    private String dcFilingReceipt;
    private String dcScope;
    private BigDecimal dcAmount;
    private String dcDeterminType;
    private String dcPriceMode;
    private BigDecimal dcUpperLimit;
    private String dcFeatures;
    private String dcExecutiveStandard;
    private String dcSourceFunds;
    private String dcPerformanceGuaranteeType;
    private BigDecimal dcPerformanceBond;
    private Float dcPerfBondRatio;
    private String dcRemark;
    private String createUname;
    private String createUid;
    private Date createTime;
    private Integer state;
    private Date stateTime;

    private String secName;
    private String dcPreparedByName;
    private String dcPreparedByAbbName;
    private List<SecProjectResponse> secProjects;

}
