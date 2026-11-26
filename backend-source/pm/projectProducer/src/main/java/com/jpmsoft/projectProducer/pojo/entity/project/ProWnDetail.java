package com.jpmsoft.projectProducer.pojo.entity.project;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("pro_wn_detail")
public class ProWnDetail {

    @TableId
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