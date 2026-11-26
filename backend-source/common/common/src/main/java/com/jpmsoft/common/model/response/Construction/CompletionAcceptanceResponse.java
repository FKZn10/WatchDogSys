package com.jpmsoft.common.model.response.Construction;

import com.jpmsoft.common.model.ProjectCommonRes;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 工程验收
 */
public class CompletionAcceptanceResponse extends ProjectCommonRes implements Serializable {

    private static final long serialVersionUID = 5415928816808060991L;

    private String caId;
    private String caContractorId;
    private String caSupervisorId;
    private String caType;
    private String pId;
    private String conId;
    private Date caDate;
    private String caContent;
    private String caBuilder;
    private String caStaffs;
    private String caCreateUid;
    private String caCreateUname;
    private Date caCreateTime;
    private Integer caState;
    private Date caStateTime;
    private String caDoc;
    private Integer caCheckResult;
    private String caCheckUid;
    private String caCheckUname;
    private Date caCheckTime;
    private String caCheckContent;
    private String caCheckDoc;
    private Integer caMeeting;
    private Date caMeetingTime;
    private String caMeetingTitle;
    private String caMeetingMember;
    private String caMeetingDoc;
    private String caRectifyProblem;
    private String caRectifyContent;
    private Integer caRectifyResult;
    private String caRectifyDoc;
    private Integer caCategory;
    private String caInformation;
    private Boolean caContractorPromise;
    private String caContractorPromiseText;
    private Boolean caSupervisorPromise;
    private String caSupervisorPromiseText;

    private String secId;
    private BigDecimal caSettlementMoney;


    private String conName;
    private String secName;
    private String secGroupName;
    private BigDecimal conMoneyTotal;
    private String caContractorName;
    private String caSupervisorName;
    private String caContractorAbbName;
    private String caSupervisorAbbName;
    private String caContractorNameHistory;
    private String caSupervisorNameHistory;
    private String caFileStand;
    private String caFileContract;
    private String caFileArchives;
    private String caFileExperimental;
    private String caFileReport;
    private String caFileSettlement;
    private String caFileSupData;
    private String caFileCheckRectfy;
    private String caFileRectfy;
    private String caFilePlan;
    private String caABook;
    private String caOtherDoc;
    private String caApplicant;
    private Date caPlanStartDate;
    private Date caPlanEndDate;
    private Date caActualStartDate;
    private Date caActualEndDate;
    private BigDecimal caConMoney;
    private String caConFile;
    private String caTechnologyFile;
    private String caQualifiedFile;
    private String caSelfCheckFile;
    private String caPreAcceptance;
    private String caAcceptance;
    private String caSettlementFile;
    private String caApplicantName;
    private String caApplicantAbbName;
    private List<CompletionAcceptanceCompanyVo> compAccepCompanys;
    private List<CompletionAcceptanceSectionVo> completionAcceptanceSections;
    private List<CompletionAcceptanceContractVo> completionAcceptanceContracts;

    private Date commencementTime;

    public Date getCommencementTime() {
        return commencementTime;
    }

    public void setCommencementTime(Date commencementTime) {
        this.commencementTime = commencementTime;
    }

    public String getCaId() {
        return caId;
    }

    public void setCaId(String caId) {
        this.caId = caId;
    }

    public String getCaContractorId() {
        return caContractorId;
    }

    public void setCaContractorId(String caContractorId) {
        this.caContractorId = caContractorId;
    }

    public String getCaSupervisorId() {
        return caSupervisorId;
    }

    public void setCaSupervisorId(String caSupervisorId) {
        this.caSupervisorId = caSupervisorId;
    }

    public String getCaType() {
        return caType;
    }

    public void setCaType(String caType) {
        this.caType = caType;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public Date getCaDate() {
        return caDate;
    }

    public void setCaDate(Date caDate) {
        this.caDate = caDate;
    }

    public String getCaContent() {
        return caContent;
    }

    public void setCaContent(String caContent) {
        this.caContent = caContent;
    }

    public String getCaBuilder() {
        return caBuilder;
    }

    public void setCaBuilder(String caBuilder) {
        this.caBuilder = caBuilder;
    }

    public String getCaStaffs() {
        return caStaffs;
    }

    public void setCaStaffs(String caStaffs) {
        this.caStaffs = caStaffs;
    }

    public String getCaCreateUid() {
        return caCreateUid;
    }

    public void setCaCreateUid(String caCreateUid) {
        this.caCreateUid = caCreateUid;
    }

    public String getCaCreateUname() {
        return caCreateUname;
    }

    public void setCaCreateUname(String caCreateUname) {
        this.caCreateUname = caCreateUname;
    }

    public Date getCaCreateTime() {
        return caCreateTime;
    }

    public void setCaCreateTime(Date caCreateTime) {
        this.caCreateTime = caCreateTime;
    }

    public Integer getCaState() {
        return caState;
    }

    public void setCaState(Integer caState) {
        this.caState = caState;
    }

    public Date getCaStateTime() {
        return caStateTime;
    }

    public void setCaStateTime(Date caStateTime) {
        this.caStateTime = caStateTime;
    }

    public String getCaDoc() {
        return caDoc;
    }

    public void setCaDoc(String caDoc) {
        this.caDoc = caDoc;
    }

    public Integer getCaCheckResult() {
        return caCheckResult;
    }

    public void setCaCheckResult(Integer caCheckResult) {
        this.caCheckResult = caCheckResult;
    }

    public String getCaCheckUid() {
        return caCheckUid;
    }

    public void setCaCheckUid(String caCheckUid) {
        this.caCheckUid = caCheckUid;
    }

    public String getCaCheckUname() {
        return caCheckUname;
    }

    public void setCaCheckUname(String caCheckUname) {
        this.caCheckUname = caCheckUname;
    }

    public Date getCaCheckTime() {
        return caCheckTime;
    }

    public void setCaCheckTime(Date caCheckTime) {
        this.caCheckTime = caCheckTime;
    }

    public String getCaCheckContent() {
        return caCheckContent;
    }

    public void setCaCheckContent(String caCheckContent) {
        this.caCheckContent = caCheckContent;
    }

    public String getCaCheckDoc() {
        return caCheckDoc;
    }

    public void setCaCheckDoc(String caCheckDoc) {
        this.caCheckDoc = caCheckDoc;
    }

    public Integer getCaMeeting() {
        return caMeeting;
    }

    public void setCaMeeting(Integer caMeeting) {
        this.caMeeting = caMeeting;
    }

    public Date getCaMeetingTime() {
        return caMeetingTime;
    }

    public void setCaMeetingTime(Date caMeetingTime) {
        this.caMeetingTime = caMeetingTime;
    }

    public String getCaMeetingTitle() {
        return caMeetingTitle;
    }

    public void setCaMeetingTitle(String caMeetingTitle) {
        this.caMeetingTitle = caMeetingTitle;
    }

    public String getCaMeetingMember() {
        return caMeetingMember;
    }

    public void setCaMeetingMember(String caMeetingMember) {
        this.caMeetingMember = caMeetingMember;
    }

    public String getCaMeetingDoc() {
        return caMeetingDoc;
    }

    public void setCaMeetingDoc(String caMeetingDoc) {
        this.caMeetingDoc = caMeetingDoc;
    }

    public String getCaRectifyProblem() {
        return caRectifyProblem;
    }

    public void setCaRectifyProblem(String caRectifyProblem) {
        this.caRectifyProblem = caRectifyProblem;
    }

    public String getCaRectifyContent() {
        return caRectifyContent;
    }

    public void setCaRectifyContent(String caRectifyContent) {
        this.caRectifyContent = caRectifyContent;
    }

    public Integer getCaRectifyResult() {
        return caRectifyResult;
    }

    public void setCaRectifyResult(Integer caRectifyResult) {
        this.caRectifyResult = caRectifyResult;
    }

    public String getCaRectifyDoc() {
        return caRectifyDoc;
    }

    public void setCaRectifyDoc(String caRectifyDoc) {
        this.caRectifyDoc = caRectifyDoc;
    }

    public Integer getCaCategory() {
        return caCategory;
    }

    public void setCaCategory(Integer caCategory) {
        this.caCategory = caCategory;
    }

    public String getCaInformation() {
        return caInformation;
    }

    public void setCaInformation(String caInformation) {
        this.caInformation = caInformation;
    }

    public Boolean getCaContractorPromise() {
        return caContractorPromise;
    }

    public void setCaContractorPromise(Boolean caContractorPromise) {
        this.caContractorPromise = caContractorPromise;
    }

    public String getCaContractorPromiseText() {
        return caContractorPromiseText;
    }

    public void setCaContractorPromiseText(String caContractorPromiseText) {
        this.caContractorPromiseText = caContractorPromiseText;
    }

    public Boolean getCaSupervisorPromise() {
        return caSupervisorPromise;
    }

    public void setCaSupervisorPromise(Boolean caSupervisorPromise) {
        this.caSupervisorPromise = caSupervisorPromise;
    }

    public String getCaSupervisorPromiseText() {
        return caSupervisorPromiseText;
    }

    public void setCaSupervisorPromiseText(String caSupervisorPromiseText) {
        this.caSupervisorPromiseText = caSupervisorPromiseText;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public BigDecimal getCaSettlementMoney() {
        return caSettlementMoney;
    }

    public void setCaSettlementMoney(BigDecimal caSettlementMoney) {
        this.caSettlementMoney = caSettlementMoney;
    }


    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    public String getSecGroupName() {
        return secGroupName;
    }

    public void setSecGroupName(String secGroupName) {
        this.secGroupName = secGroupName;
    }

    public BigDecimal getConMoneyTotal() {
        return conMoneyTotal;
    }

    public void setConMoneyTotal(BigDecimal conMoneyTotal) {
        this.conMoneyTotal = conMoneyTotal;
    }

    public String getCaContractorName() {
        return caContractorName;
    }

    public void setCaContractorName(String caContractorName) {
        this.caContractorName = caContractorName;
    }

    public String getCaSupervisorName() {
        return caSupervisorName;
    }

    public void setCaSupervisorName(String caSupervisorName) {
        this.caSupervisorName = caSupervisorName;
    }

    public String getCaContractorAbbName() {
        return caContractorAbbName;
    }

    public void setCaContractorAbbName(String caContractorAbbName) {
        this.caContractorAbbName = caContractorAbbName;
    }

    public String getCaSupervisorAbbName() {
        return caSupervisorAbbName;
    }

    public void setCaSupervisorAbbName(String caSupervisorAbbName) {
        this.caSupervisorAbbName = caSupervisorAbbName;
    }

    public String getCaContractorNameHistory() {
        return caContractorNameHistory;
    }

    public void setCaContractorNameHistory(String caContractorNameHistory) {
        this.caContractorNameHistory = caContractorNameHistory;
    }

    public String getCaSupervisorNameHistory() {
        return caSupervisorNameHistory;
    }

    public void setCaSupervisorNameHistory(String caSupervisorNameHistory) {
        this.caSupervisorNameHistory = caSupervisorNameHistory;
    }

    public String getCaFileStand() {
        return caFileStand;
    }

    public void setCaFileStand(String caFileStand) {
        this.caFileStand = caFileStand;
    }

    public String getCaFileContract() {
        return caFileContract;
    }

    public void setCaFileContract(String caFileContract) {
        this.caFileContract = caFileContract;
    }

    public String getCaFileArchives() {
        return caFileArchives;
    }

    public void setCaFileArchives(String caFileArchives) {
        this.caFileArchives = caFileArchives;
    }

    public String getCaFileExperimental() {
        return caFileExperimental;
    }

    public void setCaFileExperimental(String caFileExperimental) {
        this.caFileExperimental = caFileExperimental;
    }

    public String getCaFileReport() {
        return caFileReport;
    }

    public void setCaFileReport(String caFileReport) {
        this.caFileReport = caFileReport;
    }

    public String getCaFileSettlement() {
        return caFileSettlement;
    }

    public void setCaFileSettlement(String caFileSettlement) {
        this.caFileSettlement = caFileSettlement;
    }

    public String getCaFileSupData() {
        return caFileSupData;
    }

    public void setCaFileSupData(String caFileSupData) {
        this.caFileSupData = caFileSupData;
    }

    public String getCaFileCheckRectfy() {
        return caFileCheckRectfy;
    }

    public void setCaFileCheckRectfy(String caFileCheckRectfy) {
        this.caFileCheckRectfy = caFileCheckRectfy;
    }

    public String getCaFileRectfy() {
        return caFileRectfy;
    }

    public void setCaFileRectfy(String caFileRectfy) {
        this.caFileRectfy = caFileRectfy;
    }

    public String getCaFilePlan() {
        return caFilePlan;
    }

    public void setCaFilePlan(String caFilePlan) {
        this.caFilePlan = caFilePlan;
    }

    public String getCaABook() {
        return caABook;
    }

    public void setCaABook(String caABook) {
        this.caABook = caABook;
    }

    public String getCaOtherDoc() {
        return caOtherDoc;
    }

    public void setCaOtherDoc(String caOtherDoc) {
        this.caOtherDoc = caOtherDoc;
    }

    public String getCaApplicant() {
        return caApplicant;
    }

    public void setCaApplicant(String caApplicant) {
        this.caApplicant = caApplicant;
    }

    public Date getCaPlanStartDate() {
        return caPlanStartDate;
    }

    public void setCaPlanStartDate(Date caPlanStartDate) {
        this.caPlanStartDate = caPlanStartDate;
    }

    public Date getCaPlanEndDate() {
        return caPlanEndDate;
    }

    public void setCaPlanEndDate(Date caPlanEndDate) {
        this.caPlanEndDate = caPlanEndDate;
    }

    public Date getCaActualStartDate() {
        return caActualStartDate;
    }

    public void setCaActualStartDate(Date caActualStartDate) {
        this.caActualStartDate = caActualStartDate;
    }

    public Date getCaActualEndDate() {
        return caActualEndDate;
    }

    public void setCaActualEndDate(Date caActualEndDate) {
        this.caActualEndDate = caActualEndDate;
    }

    public BigDecimal getCaConMoney() {
        return caConMoney;
    }

    public void setCaConMoney(BigDecimal caConMoney) {
        this.caConMoney = caConMoney;
    }

    public String getCaConFile() {
        return caConFile;
    }

    public void setCaConFile(String caConFile) {
        this.caConFile = caConFile;
    }

    public String getCaTechnologyFile() {
        return caTechnologyFile;
    }

    public void setCaTechnologyFile(String caTechnologyFile) {
        this.caTechnologyFile = caTechnologyFile;
    }

    public String getCaQualifiedFile() {
        return caQualifiedFile;
    }

    public void setCaQualifiedFile(String caQualifiedFile) {
        this.caQualifiedFile = caQualifiedFile;
    }

    public String getCaSelfCheckFile() {
        return caSelfCheckFile;
    }

    public void setCaSelfCheckFile(String caSelfCheckFile) {
        this.caSelfCheckFile = caSelfCheckFile;
    }

    public String getCaPreAcceptance() {
        return caPreAcceptance;
    }

    public void setCaPreAcceptance(String caPreAcceptance) {
        this.caPreAcceptance = caPreAcceptance;
    }

    public String getCaAcceptance() {
        return caAcceptance;
    }

    public void setCaAcceptance(String caAcceptance) {
        this.caAcceptance = caAcceptance;
    }

    public String getCaApplicantName() {
        return caApplicantName;
    }

    public void setCaApplicantName(String caApplicantName) {
        this.caApplicantName = caApplicantName;
    }

    public String getCaSettlementFile() {
        return caSettlementFile;
    }

    public void setCaSettlementFile(String caSettlementFile) {
        this.caSettlementFile = caSettlementFile;
    }

    public String getCaApplicantAbbName() {
        return caApplicantAbbName;
    }

    public void setCaApplicantAbbName(String caApplicantAbbName) {
        this.caApplicantAbbName = caApplicantAbbName;
    }

    public List<CompletionAcceptanceCompanyVo> getCompAccepCompanys() {
        return compAccepCompanys;
    }

    public void setCompAccepCompanys(List<CompletionAcceptanceCompanyVo> compAccepCompanys) {
        this.compAccepCompanys = compAccepCompanys;
    }

    public List<CompletionAcceptanceSectionVo> getCompletionAcceptanceSections() {
        return completionAcceptanceSections;
    }

    public void setCompletionAcceptanceSections(List<CompletionAcceptanceSectionVo> completionAcceptanceSections) {
        this.completionAcceptanceSections = completionAcceptanceSections;
    }

    public List<CompletionAcceptanceContractVo> getCompletionAcceptanceContracts() {
        return completionAcceptanceContracts;
    }

    public void setCompletionAcceptanceContracts(List<CompletionAcceptanceContractVo> completionAcceptanceContracts) {
        this.completionAcceptanceContracts = completionAcceptanceContracts;
    }
}
