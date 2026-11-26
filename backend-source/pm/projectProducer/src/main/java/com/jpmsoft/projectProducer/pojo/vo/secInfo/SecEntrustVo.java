package com.jpmsoft.projectProducer.pojo.vo.secInfo;

import com.jpmsoft.common.model.BaseFileResponse;
import com.jpmsoft.common.model.response.SecInfo.SecProjectResponse;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 直接委托
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/31 10:59
 */
@Data
public class SecEntrustVo extends BaseFileResponse implements Serializable {

    private static final long serialVersionUID = -1152005532144509531L;

    private String id;
    private String createUname;
    private String createUid;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    private String secId;
    private String seResult;
    private String seCpyId;
    private BigDecimal seAmt;
    private String sePm;
    private String seQualification;
    private String seAchievement;
    private String seRemark;

    private String seCpyName;
    private String seCpyAbbName;
    private String secName;
    private String secType;
    private String secOrganizeForm;
    private String secBiddingType;
    private Date secStartTime;
    private Integer secBiddingCycle;
    private String secPlace;
    private String tendereeName;
    private String tendereeAbbName;
    private String secTradingPlace;
    private String secAgencyName;
    private List<SecProjectResponse> secProjects;
}
