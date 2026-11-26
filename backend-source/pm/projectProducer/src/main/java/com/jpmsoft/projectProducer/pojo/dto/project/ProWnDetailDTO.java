package com.jpmsoft.projectProducer.pojo.dto.project;

import com.jpmsoft.common.model.ProjectCommonRes;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProWnDetailDTO extends ProjectCommonRes implements Serializable {

    private static final long serialVersionUID = 1L;

    private String pwId;
    private String createUid;
    private String createUname;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    private String secAddress;
    private String secContractor;
    private BigDecimal secAmount;
    private String pwPid;
    private String secName;
    private Date pwDate;
    private String prwDate;
    private String projectOverview;
    private String siteLayout;
    private String qualSafeResp;
    private String safeProd;
    private String safeMgmtNet;
    private String hazardNotice;
    private String zeroAccidentRec;
    private String civilConstruct;
    private String envProtect;
    private String lifeQualResp;
    private String pwdDec;
    private String pwdType;
    private String pwdDesignUnit;
    private String pwdSupervisoryUnit;

}