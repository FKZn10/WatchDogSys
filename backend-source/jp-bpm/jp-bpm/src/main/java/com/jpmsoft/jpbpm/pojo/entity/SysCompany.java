package com.jpmsoft.jpbpm.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("SYS_Company")
public class SysCompany {
    @TableId(type = IdType.INPUT)
    private String cId;
    private String cName;
    private String cCode;
    private String cType;
    private String cCreditcode;
    private String cUnitlegalperson;
    private String cContactperson;
    private String cContactpersontel;
    private String cWebsite;
    private String cState;
    private String cMdmNo;
    private String cRemark;
    private String cSolution;
    private Date cCreatetime;
    private String cCreateuId;
    private String cCreateuName;
    private String cBusinessLicense;
    private String cQualification;
    private String cCredentials;
    private String cNameHistory;
    private String cAbbreviation;
    private String teId;
    private String cGroupCode;
}
