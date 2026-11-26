package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 专家删除申请
 */
@Data
@TableName("Sec_Expert_Delete")
public class SecExpertDelete {
    @TableId(type = IdType.INPUT)
    private String id;
    private String exId;
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
    private String exType;
    private String exQualification;
    private String exDiplomaMajor;
    private Date exWorkTime;
    private Integer exWorkYear;
    private Integer exWorkMon;
    private String exCpy;
    private String exCpyPost;
    private String exBank;
    private String exAvoidFile;
    private String exOtherFile;
    private BigDecimal exPostNum;
    private String exCardFile;
    private String exTitleFile;
    private Date exFinishDate;
    private String exPromiseFile;
    private String exPerformFile;
    private String exDegree;
    private String exBankAccount;
    private String exPlaintextNum;
    private String teId;
    private BigDecimal exMajorNum;
    private Short exIsAvoidance;
    private String exApplicantId;
    private BigDecimal exSysWorkYear;
    private String exDeclareMajorOne;
    private String exDeclareMajorTwo;
    private String exAvoid;
    private String personnelState;
    private Date personnelTimeStr;
    private Date personnelTimeEnd;
    private String keyExpert;
    private Integer exAge;
    private String exRank;
    private String exModifyReason;
    private String exRemark;
    private Integer year;
}