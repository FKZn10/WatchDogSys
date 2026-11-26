package com.jpmsoft.common.model.requestAndResponse.SecInfo;

import com.jpmsoft.common.annotations.ClassInfo;
import com.jpmsoft.common.model.BaseFileRequest;
import com.jpmsoft.common.model.request.SecInfo.SecExpertCertificateDto;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 专家库
 */
@Data
public class SecExpertModel extends BaseFileRequest {

    private String exId;
    private String uId;
    private String uName;
    private String cId;
    private String exName;
    @ClassInfo(title = "姓名", type = "string", value = "exNames", dbValue = "mn.Ex_Name", no = 1)
    private List<String> exNames;
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
    @ClassInfo(title = "专业技术职称", type = "dictionary", value = "exTitles", dbValue = "mn.Ex_Title",dictionary = "JPMsoft_ZYJSZC", no = 3)
    private List<String> exTitles;
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
    // @ClassInfo(title = "流程状态", type = "dictionary", value = "exStates", dictionary = "JPMsoft_LCZT", dbValue = "mn.Ex_State", no = 8)
    private List<String> exStates;
    private Date exStateTime;
    private String exCategory;
    private Date exStartDate;
    private Date exTilteDate;
    private Date exPracticeDate;
    private String exPracticeCode;
    private String exMainWork;
    private String exPersonalResult;
    private Integer exStatus;
    @ClassInfo(title = "状态", type = "dictionary", value = "exStatusList", dictionary = "JPMsoft_QJYZT", dbValue = "mn.Ex_Status", no = 7)
    private List<String> exStatusList;
    private String exType;
    //北京工行 获取职业资格时间 格式 yyyy-MM/yyyy-MM
    private String exQualification;
    /**
     * 毕业证书专业
     */
    private String exDiplomaMajor;
    /**
     * 首次参加工作时间
     */
    private Date exWorkTime;
    /**
     * 工作年限(年)
     */
    private Integer exWorkYear;
    @ClassInfo(title = "工作年限", type = "string", value = "exWorkYears", dbValue = "mn.Ex_Work_Year", no = 4)
    private List<String> exWorkYears;
    /**
     * 工作年限(月)
     */
    private Integer exWorkMon;
    /**
     * 所在单位
     */
    private String exCpy;
    /**
     * 所在单位职务
     */
    private String exCpyPost;
    private Date exFinishDate;
    private BigDecimal exPostNum;
    private BigDecimal exMajorNum;
    private String exBankAccount;
    private String exBank;
    private String exCardFile;
    private String exDegree;
    private String exPerformFile;
    private String exTitleFile;
    private String exPromiseFile;
    private String exAvoidFile;
    private String exOtherFile;
    private Boolean exIsAvoidance;
    private String exApplicantId;
    private Integer exSysWorkYear;
    private String exDeclareMajorOne;
    private String exDeclareMajorTwo;
    private String exAvoid;

    private String exCardFileIds;
    private String exDegreeIds;
    private String exPerformFileIds;
    private String exTitleFileIds;
    private String exPromiseFileIds;
    private String exAvoidFileIds;
    private String exOtherFileIds;

    private String exApplicantCId;
    private String exApplicantCName;
    private String  exApplicantName;
    private String cName;
    @ClassInfo(title = "现工作单位", type = "string", value = "cNames", dbValue = "c1.C_Name", no = 2)
    private List<String> cNames;
    private String cNameHistory;
    private String exCellphoneNumber;
    //单位简称
    private String cAbbName;
    private List<SecExpertCertificateDto> secExpertCertificates;

    private String personnelState;

    private Date personnelTimeStr;

    private Date personnelTimeEnd;

    // "01" 表示内部专家  "02" 表示外部专家
    private String experType;

    private Date extractTime;

    private String exDId; // 所属部门ID
    private String exDName;

    /**
     * 重点专家 0-否 1-是
     */
    private String keyExpert;

    @ClassInfo(title = "重点专家", type = "dictionary", value = "keyExperts", dictionary = "JPMsoft_TOF", dbValue = "mn.key_expert", no = 8)
    private List<String> keyExperts;

    private String seaId;

    private Integer exAge;
    @ClassInfo(title = "年龄", type = "string", value = "exAges", dbValue = "mn.Ex_Age", no = 5)
    private List<String> exAges;

    private String exRank;
    @ClassInfo(title = "职级", type = "dictionary", value = "exRanks", dictionary = "JPMsoft_ZJ", dbValue = "mn.Ex_Rank", no = 6)
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

    private List<String> exChangeStatusList;

    private String notSeaId;

    private String changeState;

    private String changeId;

    private String deleteState;

    private String deleteId;

    private String authSql;

    // 用于权限控制
    private String exApplicantId1;

    // 用于权限控制
    private String exApplicantCId1;

    /**
     * 是否需要校验企业是否提交
     */
    private String needAuditAuth;

    /**
     * 排除作废
     */
    private String excludeInvalid;

    public List<String> getcNames() {
        return cNames;
    }

    public void setcNames(List<String> cNames) {
        this.cNames = cNames;
    }

    public Integer getSepDaysYear() {
        return sepDaysYear;
    }

    public void setSepDaysYear(Integer sepDaysYear) {
        this.sepDaysYear = sepDaysYear;
    }

    public String getExPlaintextNum() {
        return exPlaintextNum;
    }

    public void setExPlaintextNum(String exPlaintextNum) {
        this.exPlaintextNum = exPlaintextNum;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getExAge() {
        return exAge;
    }

    public void setExAge(Integer exAge) {
        this.exAge = exAge;
    }

    public String getExRank() {
        return exRank;
    }

    public void setExRank(String exRank) {
        this.exRank = exRank;
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

    public String getSeaId() {
        return seaId;
    }

    public void setSeaId(String seaId) {
        this.seaId = seaId;
    }

    public String getKeyExpert() {
        return keyExpert;
    }

    public void setKeyExpert(String keyExpert) {
        this.keyExpert = keyExpert;
    }

    public String getExDId() {
        return exDId;
    }

    public void setExDId(String exDId) {
        this.exDId = exDId;
    }

    public String getExDName() {
        return exDName;
    }

    public void setExDName(String exDName) {
        this.exDName = exDName;
    }
    public Date getExtractTime() {
        return extractTime;
    }

    public void setExtractTime(Date extractTime) {
        this.extractTime = extractTime;
    }

    public String getExperType() {
        return experType;
    }

    public void setExperType(String experType) {
        this.experType = experType;
    }

    public Date getPersonnelTimeStr() {
        return personnelTimeStr;
    }

    public void setPersonnelTimeStr(Date personnelTimeStr) {
        this.personnelTimeStr = personnelTimeStr;
    }

    public Date getPersonnelTimeEnd() {
        return personnelTimeEnd;
    }

    public void setPersonnelTimeEnd(Date personnelTimeEnd) {
        this.personnelTimeEnd = personnelTimeEnd;
    }

    public String getPersonnelState() {
        return personnelState;
    }

    public void setPersonnelState(String personnelState) {
        this.personnelState = personnelState;
    }

    public String getExId() {
        return exId;
    }

    public void setExId(String exId) {
        this.exId = exId;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    @Override
    public String getcId() {
        return cId;
    }

    @Override
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

    public String getExDiplomaMajor() {
        return exDiplomaMajor;
    }

    public void setExDiplomaMajor(String exDiplomaMajor) {
        this.exDiplomaMajor = exDiplomaMajor;
    }

    public Date getExWorkTime() {
        return exWorkTime;
    }

    public void setExWorkTime(Date exWorkTime) {
        this.exWorkTime = exWorkTime;
    }

    public Integer getExWorkYear() {
        return exWorkYear;
    }

    public void setExWorkYear(Integer exWorkYear) {
        this.exWorkYear = exWorkYear;
    }

    public Integer getExWorkMon() {
        return exWorkMon;
    }

    public void setExWorkMon(Integer exWorkMon) {
        this.exWorkMon = exWorkMon;
    }

    public String getExCpy() {
        return exCpy;
    }

    public void setExCpy(String exCpy) {
        this.exCpy = exCpy;
    }

    public String getExCpyPost() {
        return exCpyPost;
    }

    public void setExCpyPost(String exCpyPost) {
        this.exCpyPost = exCpyPost;
    }

    public Date getExFinishDate() {
        return exFinishDate;
    }

    public void setExFinishDate(Date exFinishDate) {
        this.exFinishDate = exFinishDate;
    }

    public BigDecimal getExPostNum() {
        return exPostNum;
    }

    public void setExPostNum(BigDecimal exPostNum) {
        this.exPostNum = exPostNum;
    }

    public BigDecimal getExMajorNum() {
        return exMajorNum;
    }

    public void setExMajorNum(BigDecimal exMajorNum) {
        this.exMajorNum = exMajorNum;
    }

    public String getExBankAccount() {
        return exBankAccount;
    }

    public void setExBankAccount(String exBankAccount) {
        this.exBankAccount = exBankAccount;
    }

    public String getExBank() {
        return exBank;
    }

    public void setExBank(String exBank) {
        this.exBank = exBank;
    }

    public String getExCardFile() {
        return exCardFile;
    }

    public void setExCardFile(String exCardFile) {
        this.exCardFile = exCardFile;
    }

    public String getExDegree() {
        return exDegree;
    }

    public void setExDegree(String exDegree) {
        this.exDegree = exDegree;
    }

    public String getExPerformFile() {
        return exPerformFile;
    }

    public void setExPerformFile(String exPerformFile) {
        this.exPerformFile = exPerformFile;
    }

    public String getExTitleFile() {
        return exTitleFile;
    }

    public void setExTitleFile(String exTitleFile) {
        this.exTitleFile = exTitleFile;
    }

    public String getExPromiseFile() {
        return exPromiseFile;
    }

    public void setExPromiseFile(String exPromiseFile) {
        this.exPromiseFile = exPromiseFile;
    }

    public String getExAvoidFile() {
        return exAvoidFile;
    }

    public void setExAvoidFile(String exAvoidFile) {
        this.exAvoidFile = exAvoidFile;
    }

    public String getExOtherFile() {
        return exOtherFile;
    }

    public void setExOtherFile(String exOtherFile) {
        this.exOtherFile = exOtherFile;
    }

    public Boolean getExIsAvoidance() {
        return exIsAvoidance;
    }

    public void setExIsAvoidance(Boolean exIsAvoidance) {
        this.exIsAvoidance = exIsAvoidance;
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

    public String getExCardFileIds() {
        return exCardFileIds;
    }

    public void setExCardFileIds(String exCardFileIds) {
        this.exCardFileIds = exCardFileIds;
    }

    public String getExDegreeIds() {
        return exDegreeIds;
    }

    public void setExDegreeIds(String exDegreeIds) {
        this.exDegreeIds = exDegreeIds;
    }

    public String getExPerformFileIds() {
        return exPerformFileIds;
    }

    public void setExPerformFileIds(String exPerformFileIds) {
        this.exPerformFileIds = exPerformFileIds;
    }

    public String getExTitleFileIds() {
        return exTitleFileIds;
    }

    public void setExTitleFileIds(String exTitleFileIds) {
        this.exTitleFileIds = exTitleFileIds;
    }

    public String getExPromiseFileIds() {
        return exPromiseFileIds;
    }

    public void setExPromiseFileIds(String exPromiseFileIds) {
        this.exPromiseFileIds = exPromiseFileIds;
    }

    public String getExAvoidFileIds() {
        return exAvoidFileIds;
    }

    public void setExAvoidFileIds(String exAvoidFileIds) {
        this.exAvoidFileIds = exAvoidFileIds;
    }

    public String getExOtherFileIds() {
        return exOtherFileIds;
    }

    public void setExOtherFileIds(String exOtherFileIds) {
        this.exOtherFileIds = exOtherFileIds;
    }

    public String getExApplicantCId() {
        return exApplicantCId;
    }

    public void setExApplicantCId(String exApplicantCId) {
        this.exApplicantCId = exApplicantCId;
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

    public String getcAbbName() {
        return cAbbName;
    }

    public void setcAbbName(String cAbbName) {
        this.cAbbName = cAbbName;
    }

    public List<SecExpertCertificateDto> getSecExpertCertificates() {
        return secExpertCertificates;
    }

    public void setSecExpertCertificates(List<SecExpertCertificateDto> secExpertCertificates) {
        this.secExpertCertificates = secExpertCertificates;
    }
}
