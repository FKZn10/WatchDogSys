package com.jpmsoft.projectProducer.pojo.vo.secInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/11/27 14:55
 */
@Data
public class SecSupplementRecordDtlVO {

    private String srdId;
    private String exId;
    private String sesId;
    private Integer srdCount;
    /**
     * 选取类型(抽取|自选)
     */
    private String srdType;
    private Integer srdAvoid;
    private Integer srdAttend;
    private Integer srdVacation;
    private Date srdTime;
    private String srdMark;

    @JsonProperty("cId")
    private String cId;
    private String exName;
    private String exSex;
    private Date exBirthday;
    private String exFamily;
    private String exIdType;
    private String exIdNumber;
    private String exLocation;
    private String exPolitics;
    private String exEducation;
    private String exMajor;
    private String exSchool;
    private String exWorkState;
    private Integer exProfessionalYears;
    private String exWorkUnit;
    private String exPost;
    private String exTitle;
    private String exTitleNumber;
    private String exProfessionalCategory;
    private String exIndustry;
    private String exWorkAddress;
    private String exCellphoneNumber1;
    private String exCellphoneNumber2;
    private String exPostcode;
    private String exFax;
    private String exHomePhone;
    private String exEmail;
    private String exMailAddress;
    private String exBidMajor;
    private String exPracticeQualification;
    private String exBidArea;
    private String exPersonalResearch;
    private String exProjects;
    private String exAvoidanceUnit;
    private String exCreateUid;
    private String exCreateUname;
    private Date exCreateTime;
    private Integer exState;
    private Date exStateTime;
    private String exCategory;
    private Date exStartDate;
    private Date exTilteDate;
    private Date exPracticeDate;
    private String exPracticeCode;
    private String exMainWork;
    private String exPersonalResult;
    private Integer exStatus;
    @JsonProperty("cName")
    private String cName;
    @JsonProperty("cNameHistory")
    private String cNameHistory;
    private String exCellphoneNumber;
    //单位简称
    private String exContrAbbName;
    private String exType;
    private String exQualification;
}
