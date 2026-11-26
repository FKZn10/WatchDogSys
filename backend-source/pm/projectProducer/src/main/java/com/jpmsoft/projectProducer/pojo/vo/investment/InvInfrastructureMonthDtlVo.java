package com.jpmsoft.projectProducer.pojo.vo.investment;

import com.jpmsoft.common.model.ProjectCommonRes;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 基建投资月报清单
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/5 9:02
 */
@Data
public class InvInfrastructureMonthDtlVo extends ProjectCommonRes implements Serializable {

    private static final long serialVersionUID = -4100536406237738824L;

    private String id;
    private String imId;
    private BigDecimal imdSurplusFunds;
    private BigDecimal imdCollectCountry;
    private BigDecimal imdCollectSchool;
    private BigDecimal imdCollectGovernment;
    private BigDecimal imdCollectDonate;
    private BigDecimal imdCollectOther;
    private BigDecimal imdPayCountry;
    private BigDecimal imdPaySchool;
    private BigDecimal imdPayGovernment;
    private BigDecimal imdPayDonate;
    private BigDecimal imdPayOther;
    private BigDecimal imdOtherFunds;
    private BigDecimal imdReturnFunds;
    private BigDecimal imdYearBalance;
    private BigDecimal imdPlanFunds;
    private String imdRemark;
    private Integer imdSort;
    private String createUid;
    private String createUname;
    private Date createTime;
    private String imdSchoolName;
    private BigDecimal imdCollectTotal;
    private BigDecimal imdPayTotal;

    private String imYear;
    private String imMon;
    private Integer state;
    private Date stateTime;
}
