package com.jpmsoft.common.model.requestAndResponse.SecInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author yb.Sun
 * @date 2024/11/14 11:10
 */
@Data
public class SecJGSelectionRecordDtlModelPrintVo  extends SecJGSelectionRecordSimpleModel {
    private String id;
    private String exId;
    private String srId;
    private Integer srdCount;
    /**
     * 选取类型(抽取|自选)
     */
    private String srdType;
    private Integer srdAvoid;
    //是否按时参加 1、按时 0、不按时
    private Integer srdAttend;
    private String srdAttendName;
    private Integer srdVacation;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date srdTime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date srdConfirmTime;
    private String srdEsType;

    private String cId;
    private String exName;
    private String exSex;
    private Date exBirthday;
    private String exFamily;
    /**
     * 是否内部专家 0：外部 1、内部
     */
    private Integer exInsideType;
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
    private String exCategoryName;
    private Date exStartDate;
    private Date exTilteDate;
    private Date exPracticeDate;
    private String exPracticeCode;
    private String exMainWork;
    private String exPersonalResult;
    private Integer exStatus;

    private String cName;
    private String cNameHistory;
    private String exCellphoneNumber;
    //单位简称
    private String exContrAbbName;
    private String exType;
    private String exQualification;
    private String exApplicantId;
    private Integer exSysWorkYear;
    private String exDeclareMajorOne;
    private String exDeclareMajorTwo;
    private String exAvoid;
}
