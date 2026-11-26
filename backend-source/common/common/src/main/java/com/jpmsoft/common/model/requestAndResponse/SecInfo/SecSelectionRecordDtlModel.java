package com.jpmsoft.common.model.requestAndResponse.SecInfo;

import com.jpmsoft.common.annotations.ClassInfo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 专家选取记录详情
 */
public class SecSelectionRecordDtlModel {
    private String srdId;
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
    private Integer srdVacation;
    private Date srdTime;
    private Date srdConfirmTime;
    private String srdEsType;

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

    private String ecId;

    private String status;

    private String detailsTxt = "评价";
    private String scdId;

    private BigDecimal totalDeduction;

    // 专家选取记录详情结果
    private Integer secdResult;

    /**
     * 重点专家 0-否 1-是
     */
    private String keyExpert;

    private String seaId;

    private Integer exAge;
    @ClassInfo(title = "年龄", type = "string", value = "exAges", dbValue = "mn.Ex_Age", no = 5)
    private List<String> exAges;

    private String exRank;
    @ClassInfo(title = "职级", type = "string", value = "exRanks", dbValue = "mn.Ex_Rank", no = 6)
    private List<String> exRanks;

    private String exModifyReason;

    private String exRemark;

    private Integer year;

    /**
     * 明文身份证号码
     */
    private String exPlaintextNum;

    private Integer sepDaysYear;

    /**
     * 专家库变更状态 00-未修改 01-新增 02-变更 03-删除 04-未提交
     */
    private String exChangeStatus;

    private Integer selectionCount;

    private String cAbbName;

    private String exApplicantCName;

    private String exApplicantName;

    private String pName;

    private String secName;

    private String pCategory;

    private Date ecCreateTime;

    private Integer ecState;

    private Integer exWorkYear;

    public Integer getExWorkYear() {
        return exWorkYear;
    }

    public void setExWorkYear(Integer exWorkYear) {
        this.exWorkYear = exWorkYear;
    }

    public Integer getEcState() {
        return ecState;
    }

    public void setEcState(Integer ecState) {
        this.ecState = ecState;
    }

    public Date getEcCreateTime() {
        return ecCreateTime;
    }

    public void setEcCreateTime(Date ecCreateTime) {
        this.ecCreateTime = ecCreateTime;
    }

    public String getpCategory() {
        return pCategory;
    }

    public void setpCategory(String pCategory) {
        this.pCategory = pCategory;
    }

    public String getcAbbName() {
        return cAbbName;
    }

    public void setcAbbName(String cAbbName) {
        this.cAbbName = cAbbName;
    }

    public String getExApplicantCName() {
        return exApplicantCName;
    }

    public void setExApplicantCName(String exApplicantCName) {
        this.exApplicantCName = exApplicantCName;
    }

    public String getExApplicantName() {
        return exApplicantName;
    }

    public void setExApplicantName(String exApplicantName) {
        this.exApplicantName = exApplicantName;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    public String getKeyExpert() {
        return keyExpert;
    }

    public void setKeyExpert(String keyExpert) {
        this.keyExpert = keyExpert;
    }

    public String getSeaId() {
        return seaId;
    }

    public void setSeaId(String seaId) {
        this.seaId = seaId;
    }

    public Integer getExAge() {
        return exAge;
    }

    public void setExAge(Integer exAge) {
        this.exAge = exAge;
    }

    public List<String> getExAges() {
        return exAges;
    }

    public void setExAges(List<String> exAges) {
        this.exAges = exAges;
    }

    public String getExRank() {
        return exRank;
    }

    public void setExRank(String exRank) {
        this.exRank = exRank;
    }

    public List<String> getExRanks() {
        return exRanks;
    }

    public void setExRanks(List<String> exRanks) {
        this.exRanks = exRanks;
    }

    public String getExModifyReason() {
        return exModifyReason;
    }

    public void setExModifyReason(String exModifyReason) {
        this.exModifyReason = exModifyReason;
    }

    public String getExRemark() {
        return exRemark;
    }

    public void setExRemark(String exRemark) {
        this.exRemark = exRemark;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getExPlaintextNum() {
        return exPlaintextNum;
    }

    public void setExPlaintextNum(String exPlaintextNum) {
        this.exPlaintextNum = exPlaintextNum;
    }

    public Integer getSepDaysYear() {
        return sepDaysYear;
    }

    public void setSepDaysYear(Integer sepDaysYear) {
        this.sepDaysYear = sepDaysYear;
    }

    public String getExChangeStatus() {
        return exChangeStatus;
    }

    public void setExChangeStatus(String exChangeStatus) {
        this.exChangeStatus = exChangeStatus;
    }

    public Integer getSelectionCount() {
        return selectionCount;
    }

    public void setSelectionCount(Integer selectionCount) {
        this.selectionCount = selectionCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getSecdResult() {
        return secdResult;
    }

    public void setSecdResult(Integer secdResult) {
        this.secdResult = secdResult;
    }

    public String getScdId() {
        return scdId;
    }

    public void setScdId(String scdId) {
        this.scdId = scdId;
    }

    public String getDetailsTxt() {
        return detailsTxt;
    }

    public void setDetailsTxt(String detailsTxt) {
        this.detailsTxt = detailsTxt;
    }

    public BigDecimal getTotalDeduction() {
        return totalDeduction;
    }

    public void setTotalDeduction(BigDecimal totalDeduction) {
        this.totalDeduction = totalDeduction;
    }



    public String getEcId() {
        return ecId;
    }

    public void setEcId(String ecId) {
        this.ecId = ecId;
    }

    private List<SecExpertCheckDtlModel> secExpertCheckDtls;

    public List<SecExpertCheckDtlModel> getSecExpertCheckDtls() {
        return secExpertCheckDtls;
    }

    public void setSecExpertCheckDtls(List<SecExpertCheckDtlModel> secExpertCheckDtls) {
        this.secExpertCheckDtls = secExpertCheckDtls;
    }

    public String getSrdId() {
        return srdId;
    }

    public void setSrdId(String srdId) {
        this.srdId = srdId;
    }

    public String getExId() {
        return exId;
    }

    public void setExId(String exId) {
        this.exId = exId;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public String getExName() {
        return exName;
    }

    public void setExName(String exName) {
        this.exName = exName;
    }

    public String getExSex() {
        return exSex;
    }

    public void setExSex(String exSex) {
        this.exSex = exSex;
    }

    public Date getExBirthday() {
        return exBirthday;
    }

    public void setExBirthday(Date exBirthday) {
        this.exBirthday = exBirthday;
    }

    public String getExFamily() {
        return exFamily;
    }

    public void setExFamily(String exFamily) {
        this.exFamily = exFamily;
    }

    public String getExIdType() {
        return exIdType;
    }

    public void setExIdType(String exIdType) {
        this.exIdType = exIdType;
    }

    public String getExIdNumber() {
        return exIdNumber;
    }

    public void setExIdNumber(String exIdNumber) {
        this.exIdNumber = exIdNumber;
    }

    public String getExLocation() {
        return exLocation;
    }

    public void setExLocation(String exLocation) {
        this.exLocation = exLocation;
    }

    public String getExPolitics() {
        return exPolitics;
    }

    public void setExPolitics(String exPolitics) {
        this.exPolitics = exPolitics;
    }

    public String getExEducation() {
        return exEducation;
    }

    public void setExEducation(String exEducation) {
        this.exEducation = exEducation;
    }

    public String getExMajor() {
        return exMajor;
    }

    public void setExMajor(String exMajor) {
        this.exMajor = exMajor;
    }

    public String getExSchool() {
        return exSchool;
    }

    public void setExSchool(String exSchool) {
        this.exSchool = exSchool;
    }

    public String getExWorkState() {
        return exWorkState;
    }

    public void setExWorkState(String exWorkState) {
        this.exWorkState = exWorkState;
    }

    public Integer getExProfessionalYears() {
        return exProfessionalYears;
    }

    public void setExProfessionalYears(Integer exProfessionalYears) {
        this.exProfessionalYears = exProfessionalYears;
    }

    public String getExWorkUnit() {
        return exWorkUnit;
    }

    public void setExWorkUnit(String exWorkUnit) {
        this.exWorkUnit = exWorkUnit;
    }

    public String getExPost() {
        return exPost;
    }

    public void setExPost(String exPost) {
        this.exPost = exPost;
    }

    public String getExTitle() {
        return exTitle;
    }

    public void setExTitle(String exTitle) {
        this.exTitle = exTitle;
    }

    public String getExTitleNumber() {
        return exTitleNumber;
    }

    public void setExTitleNumber(String exTitleNumber) {
        this.exTitleNumber = exTitleNumber;
    }

    public String getExProfessionalCategory() {
        return exProfessionalCategory;
    }

    public void setExProfessionalCategory(String exProfessionalCategory) {
        this.exProfessionalCategory = exProfessionalCategory;
    }

    public String getExIndustry() {
        return exIndustry;
    }

    public void setExIndustry(String exIndustry) {
        this.exIndustry = exIndustry;
    }

    public String getExWorkAddress() {
        return exWorkAddress;
    }

    public void setExWorkAddress(String exWorkAddress) {
        this.exWorkAddress = exWorkAddress;
    }

    public String getExCellphoneNumber1() {
        return exCellphoneNumber1;
    }

    public void setExCellphoneNumber1(String exCellphoneNumber1) {
        this.exCellphoneNumber1 = exCellphoneNumber1;
    }

    public String getExCellphoneNumber2() {
        return exCellphoneNumber2;
    }

    public void setExCellphoneNumber2(String exCellphoneNumber2) {
        this.exCellphoneNumber2 = exCellphoneNumber2;
    }

    public String getExPostcode() {
        return exPostcode;
    }

    public void setExPostcode(String exPostcode) {
        this.exPostcode = exPostcode;
    }

    public String getExFax() {
        return exFax;
    }

    public void setExFax(String exFax) {
        this.exFax = exFax;
    }

    public String getExHomePhone() {
        return exHomePhone;
    }

    public void setExHomePhone(String exHomePhone) {
        this.exHomePhone = exHomePhone;
    }

    public String getExEmail() {
        return exEmail;
    }

    public void setExEmail(String exEmail) {
        this.exEmail = exEmail;
    }

    public String getExMailAddress() {
        return exMailAddress;
    }

    public void setExMailAddress(String exMailAddress) {
        this.exMailAddress = exMailAddress;
    }

    public String getExBidMajor() {
        return exBidMajor;
    }

    public void setExBidMajor(String exBidMajor) {
        this.exBidMajor = exBidMajor;
    }

    public String getExPracticeQualification() {
        return exPracticeQualification;
    }

    public void setExPracticeQualification(String exPracticeQualification) {
        this.exPracticeQualification = exPracticeQualification;
    }

    public String getExBidArea() {
        return exBidArea;
    }

    public void setExBidArea(String exBidArea) {
        this.exBidArea = exBidArea;
    }

    public String getExPersonalResearch() {
        return exPersonalResearch;
    }

    public void setExPersonalResearch(String exPersonalResearch) {
        this.exPersonalResearch = exPersonalResearch;
    }

    public String getExProjects() {
        return exProjects;
    }

    public void setExProjects(String exProjects) {
        this.exProjects = exProjects;
    }

    public String getExAvoidanceUnit() {
        return exAvoidanceUnit;
    }

    public void setExAvoidanceUnit(String exAvoidanceUnit) {
        this.exAvoidanceUnit = exAvoidanceUnit;
    }

    public String getExCreateUid() {
        return exCreateUid;
    }

    public void setExCreateUid(String exCreateUid) {
        this.exCreateUid = exCreateUid;
    }

    public String getExCreateUname() {
        return exCreateUname;
    }

    public void setExCreateUname(String exCreateUname) {
        this.exCreateUname = exCreateUname;
    }

    public Date getExCreateTime() {
        return exCreateTime;
    }

    public void setExCreateTime(Date exCreateTime) {
        this.exCreateTime = exCreateTime;
    }

    public Integer getExState() {
        return exState;
    }

    public void setExState(Integer exState) {
        this.exState = exState;
    }

    public Date getExStateTime() {
        return exStateTime;
    }

    public void setExStateTime(Date exStateTime) {
        this.exStateTime = exStateTime;
    }

    public String getExCategory() {
        return exCategory;
    }

    public void setExCategory(String exCategory) {
        this.exCategory = exCategory;
    }

    public Date getExStartDate() {
        return exStartDate;
    }

    public void setExStartDate(Date exStartDate) {
        this.exStartDate = exStartDate;
    }

    public Date getExTilteDate() {
        return exTilteDate;
    }

    public void setExTilteDate(Date exTilteDate) {
        this.exTilteDate = exTilteDate;
    }

    public Date getExPracticeDate() {
        return exPracticeDate;
    }

    public void setExPracticeDate(Date exPracticeDate) {
        this.exPracticeDate = exPracticeDate;
    }

    public String getExPracticeCode() {
        return exPracticeCode;
    }

    public void setExPracticeCode(String exPracticeCode) {
        this.exPracticeCode = exPracticeCode;
    }

    public String getExMainWork() {
        return exMainWork;
    }

    public void setExMainWork(String exMainWork) {
        this.exMainWork = exMainWork;
    }

    public String getExPersonalResult() {
        return exPersonalResult;
    }

    public void setExPersonalResult(String exPersonalResult) {
        this.exPersonalResult = exPersonalResult;
    }

    public Integer getExStatus() {
        return exStatus;
    }

    public void setExStatus(Integer exStatus) {
        this.exStatus = exStatus;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcNameHistory() {
        return cNameHistory;
    }

    public void setcNameHistory(String cNameHistory) {
        this.cNameHistory = cNameHistory;
    }

    public String getExCellphoneNumber() {
        return exCellphoneNumber;
    }

    public void setExCellphoneNumber(String exCellphoneNumber) {
        this.exCellphoneNumber = exCellphoneNumber;
    }

    public String getSrId() {
        return srId;
    }

    public void setSrId(String srId) {
        this.srId = srId;
    }

    public Integer getSrdCount() {
        return srdCount;
    }

    public void setSrdCount(Integer srdCount) {
        this.srdCount = srdCount;
    }

    public String getSrdType() {
        return srdType;
    }

    public void setSrdType(String srdType) {
        this.srdType = srdType;
    }

    public Integer getSrdAvoid() {
        return srdAvoid;
    }

    public void setSrdAvoid(Integer srdAvoid) {
        this.srdAvoid = srdAvoid;
    }

    public Integer getSrdAttend() {
        return srdAttend;
    }

    public void setSrdAttend(Integer srdAttend) {
        this.srdAttend = srdAttend;
    }

    public Integer getSrdVacation() {
        return srdVacation;
    }

    public void setSrdVacation(Integer srdVacation) {
        this.srdVacation = srdVacation;
    }

    public Date getSrdTime() {
        return srdTime;
    }

    public void setSrdTime(Date srdTime) {
        this.srdTime = srdTime;
    }

    public Date getSrdConfirmTime() {
        return srdConfirmTime;
    }

    public void setSrdConfirmTime(Date srdConfirmTime) {
        this.srdConfirmTime = srdConfirmTime;
    }

    public String getSrdEsType() {
        return srdEsType;
    }

    public void setSrdEsType(String srdEsType) {
        this.srdEsType = srdEsType;
    }

    public String getExContrAbbName() {
        return exContrAbbName;
    }

    public void setExContrAbbName(String exContrAbbName) {
        this.exContrAbbName = exContrAbbName;
    }

    public String getExType() {
        return exType;
    }

    public void setExType(String exType) {
        this.exType = exType;
    }

    public String getExQualification() {
        return exQualification;
    }

    public void setExQualification(String exQualification) {
        this.exQualification = exQualification;
    }

    public String getExApplicantId() {
        return exApplicantId;
    }

    public void setExApplicantId(String exApplicantId) {
        this.exApplicantId = exApplicantId;
    }

    public Integer getExSysWorkYear() {
        return exSysWorkYear;
    }

    public void setExSysWorkYear(Integer exSysWorkYear) {
        this.exSysWorkYear = exSysWorkYear;
    }

    public String getExDeclareMajorOne() {
        return exDeclareMajorOne;
    }

    public void setExDeclareMajorOne(String exDeclareMajorOne) {
        this.exDeclareMajorOne = exDeclareMajorOne;
    }

    public String getExDeclareMajorTwo() {
        return exDeclareMajorTwo;
    }

    public void setExDeclareMajorTwo(String exDeclareMajorTwo) {
        this.exDeclareMajorTwo = exDeclareMajorTwo;
    }

    public String getExAvoid() {
        return exAvoid;
    }

    public void setExAvoid(String exAvoid) {
        this.exAvoid = exAvoid;
    }
}
