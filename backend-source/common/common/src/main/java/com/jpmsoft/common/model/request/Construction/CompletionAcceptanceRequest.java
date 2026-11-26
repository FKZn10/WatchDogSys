package com.jpmsoft.common.model.request.Construction;

import com.jpmsoft.common.base.FileFields;
import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.BaseFileRequest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 工程验收
 */
public class CompletionAcceptanceRequest extends BaseFileRequest {

    @ProcessFields(type = ProcessFieldEnum.id)
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
    @ProcessFields(type = ProcessFieldEnum.createUid)
    private String caCreateUid;
    @ProcessFields(type = ProcessFieldEnum.createUname)
    private String caCreateUname;
    @ProcessFields(type = ProcessFieldEnum.createTime)
    private Date caCreateTime;
    @ProcessFields(type = ProcessFieldEnum.state)
    private Integer caState;
    @ProcessFields(type = ProcessFieldEnum.stateTime)
    private Date caStateTime;
    @FileFields(index = 1)
    private String caDoc;
    private Integer caCheckResult;
    private String caCheckUid;
    private String caCheckUname;
    private Date caCheckTime;
    private String caCheckContent;
    @FileFields(index = 2)
    private String caCheckDoc;
    private Integer caMeeting;
    private Date caMeetingTime;
    private String caMeetingTitle;
    private String caMeetingMember;
    @FileFields(index = 3)
    private String caMeetingDoc;
    private String caRectifyProblem;
    private String caRectifyContent;
    private Integer caRectifyResult;
    @FileFields(index = 4)
    private String caRectifyDoc;
    private Integer caCategory;
    private String caInformation;
    private Boolean caContractorPromise;
    private String caContractorPromiseText;
    private Boolean caSupervisorPromise;
    private String caSupervisorPromiseText;

    private String caApplicant;
    private Date caPlanStartDate;
    private Date caPlanEndDate;
    private Date caActualStartDate;
    private Date caActualEndDate;
    private BigDecimal caConMoney;
    @FileFields(index = 17)
    private String caConFile;
    @FileFields(index = 18)
    private String caTechnologyFile;
    @FileFields(index = 19)
    private String caQualifiedFile;
    @FileFields(index = 20)
    private String caSelfCheckFile;
    @FileFields(index = 21)
    private String caPreAcceptance;
    private String caAcceptance;
    @FileFields(index = 22)
    private String caSettlementFile;
    private String secId;
    private BigDecimal caSettlementMoney;

    private String pName;
    private String conName;
    @FileFields(index = 1, type = "caDocIds")
    private String caDocIds;
    @FileFields(index = 2, type = "caCheckDocIds")
    private String caCheckDocIds;
    @FileFields(index = 3, type = "caMeetingDocIds")
    private String caMeetingDocIds;
    @FileFields(index = 4, type = "caRectifyDocIds")
    private String caRectifyDocIds;
    private String caContractorName;
    private List<String> caCreateTimes;
    private List<String> caDates;
    @FileFields(index = 13)
    private String caFileStand;
    @FileFields(index = 7)
    private String caFileContract;
    @FileFields(index = 5)
    private String caFileArchives;
    @FileFields(index = 8)
    private String caFileExperimental;
    @FileFields(index = 11)
    private String caFileReport;
    @FileFields(index = 12)
    private String caFileSettlement;
    @FileFields(index = 14)
    private String caFileSupData;
    @FileFields(index = 6)
    private String caFileCheckRectfy;
    @FileFields(index = 10)
    private String caFileRectfy;
    @FileFields(index = 9)
    private String caFilePlan;
    @FileFields(index = 15)
    private String caABook;
    @FileFields(index = 16)
    private String caOtherDoc;
    @FileFields(index = 15, type = "caABookIds")
    private String caABookIds;
    @FileFields(index = 16, type = "caOtherDocIds")
    private String caOtherDocIds;
    @FileFields(index = 13, type = "caFileStandIds")
    private String caFileStandIds;
    @FileFields(index = 7, type = "caFileContractIds")
    private String caFileContractIds;
    @FileFields(index = 5, type = "caFileArchivesIds")
    private String caFileArchivesIds;
    @FileFields(index = 8, type = "caFileExperimentalIds")
    private String caFileExperimentalIds;
    @FileFields(index = 11, type = "caFileReportIds")
    private String caFileReportIds;
    @FileFields(index = 12, type = "caFileSettlementIds")
    private String caFileSettlementIds;
    @FileFields(index = 14, type = "caFileSupDataIds")
    private String caFileSupDataIds;
    @FileFields(index = 6, type = "caFileCheckRectfyIds")
    private String caFileCheckRectfyIds;
    @FileFields(index = 10, type = "caFileRectfyIds")
    private String caFileRectfyIds;
    @FileFields(index = 9, type = "caFilePlanIds")
    private String caFilePlanIds;
    @FileFields(index = 17, type = "caConFileIds")
    private String caConFileIds;
    @FileFields(index = 18, type = "caTechnologyFileIds")
    private String caTechnologyFileIds;
    @FileFields(index = 19, type = "caQualifiedFileIds")
    private String caQualifiedFileIds;
    @FileFields(index = 20, type = "caSelfCheckFileIds")
    private String caSelfCheckFileIds;
    @FileFields(index = 21, type = "caPreAcceptanceIds")
    private String caPreAcceptanceIds;
    @FileFields(index = 22, type = "caSettlementFileIds")
    private String caSettlementFileIds;
    private String caApplicantName;
    private String caApplicantAbbName;
    private String secName;
    private String secGroupName;
    private List<CompletionAcceptanceCompanyDto> compAccepCompanys;
    private List<CompletionAcceptanceSectionDto> completionAcceptanceSections;
    private List<CompletionAcceptanceContractDto> completionAcceptanceContracts;

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

    @Override
    public String getpId() {
        return pId;
    }

    @Override
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

    public String getCaSettlementFile() {
        return caSettlementFile;
    }

    public void setCaSettlementFile(String caSettlementFile) {
        this.caSettlementFile = caSettlementFile;
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

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getCaDocIds() {
        return caDocIds;
    }

    public void setCaDocIds(String caDocIds) {
        this.caDocIds = caDocIds;
    }

    public String getCaCheckDocIds() {
        return caCheckDocIds;
    }

    public void setCaCheckDocIds(String caCheckDocIds) {
        this.caCheckDocIds = caCheckDocIds;
    }

    public String getCaMeetingDocIds() {
        return caMeetingDocIds;
    }

    public void setCaMeetingDocIds(String caMeetingDocIds) {
        this.caMeetingDocIds = caMeetingDocIds;
    }

    public String getCaRectifyDocIds() {
        return caRectifyDocIds;
    }

    public void setCaRectifyDocIds(String caRectifyDocIds) {
        this.caRectifyDocIds = caRectifyDocIds;
    }

    public String getCaContractorName() {
        return caContractorName;
    }

    public void setCaContractorName(String caContractorName) {
        this.caContractorName = caContractorName;
    }

    public List<String> getCaCreateTimes() {
        return caCreateTimes;
    }

    public void setCaCreateTimes(List<String> caCreateTimes) {
        this.caCreateTimes = caCreateTimes;
    }

    public List<String> getCaDates() {
        return caDates;
    }

    public void setCaDates(List<String> caDates) {
        this.caDates = caDates;
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

    public String getCaABookIds() {
        return caABookIds;
    }

    public void setCaABookIds(String caABookIds) {
        this.caABookIds = caABookIds;
    }

    public String getCaOtherDocIds() {
        return caOtherDocIds;
    }

    public void setCaOtherDocIds(String caOtherDocIds) {
        this.caOtherDocIds = caOtherDocIds;
    }

    public String getCaFileStandIds() {
        return caFileStandIds;
    }

    public void setCaFileStandIds(String caFileStandIds) {
        this.caFileStandIds = caFileStandIds;
    }

    public String getCaFileContractIds() {
        return caFileContractIds;
    }

    public void setCaFileContractIds(String caFileContractIds) {
        this.caFileContractIds = caFileContractIds;
    }

    public String getCaFileArchivesIds() {
        return caFileArchivesIds;
    }

    public void setCaFileArchivesIds(String caFileArchivesIds) {
        this.caFileArchivesIds = caFileArchivesIds;
    }

    public String getCaFileExperimentalIds() {
        return caFileExperimentalIds;
    }

    public void setCaFileExperimentalIds(String caFileExperimentalIds) {
        this.caFileExperimentalIds = caFileExperimentalIds;
    }

    public String getCaFileReportIds() {
        return caFileReportIds;
    }

    public void setCaFileReportIds(String caFileReportIds) {
        this.caFileReportIds = caFileReportIds;
    }

    public String getCaFileSettlementIds() {
        return caFileSettlementIds;
    }

    public void setCaFileSettlementIds(String caFileSettlementIds) {
        this.caFileSettlementIds = caFileSettlementIds;
    }

    public String getCaFileSupDataIds() {
        return caFileSupDataIds;
    }

    public void setCaFileSupDataIds(String caFileSupDataIds) {
        this.caFileSupDataIds = caFileSupDataIds;
    }

    public String getCaFileCheckRectfyIds() {
        return caFileCheckRectfyIds;
    }

    public void setCaFileCheckRectfyIds(String caFileCheckRectfyIds) {
        this.caFileCheckRectfyIds = caFileCheckRectfyIds;
    }

    public String getCaFileRectfyIds() {
        return caFileRectfyIds;
    }

    public void setCaFileRectfyIds(String caFileRectfyIds) {
        this.caFileRectfyIds = caFileRectfyIds;
    }

    public String getCaFilePlanIds() {
        return caFilePlanIds;
    }

    public void setCaFilePlanIds(String caFilePlanIds) {
        this.caFilePlanIds = caFilePlanIds;
    }

    public String getCaConFileIds() {
        return caConFileIds;
    }

    public void setCaConFileIds(String caConFileIds) {
        this.caConFileIds = caConFileIds;
    }

    public String getCaTechnologyFileIds() {
        return caTechnologyFileIds;
    }

    public void setCaTechnologyFileIds(String caTechnologyFileIds) {
        this.caTechnologyFileIds = caTechnologyFileIds;
    }

    public String getCaQualifiedFileIds() {
        return caQualifiedFileIds;
    }

    public void setCaQualifiedFileIds(String caQualifiedFileIds) {
        this.caQualifiedFileIds = caQualifiedFileIds;
    }

    public String getCaSelfCheckFileIds() {
        return caSelfCheckFileIds;
    }

    public void setCaSelfCheckFileIds(String caSelfCheckFileIds) {
        this.caSelfCheckFileIds = caSelfCheckFileIds;
    }

    public String getCaPreAcceptanceIds() {
        return caPreAcceptanceIds;
    }

    public void setCaPreAcceptanceIds(String caPreAcceptanceIds) {
        this.caPreAcceptanceIds = caPreAcceptanceIds;
    }

    public String getCaSettlementFileIds() {
        return caSettlementFileIds;
    }

    public void setCaSettlementFileIds(String caSettlementFileIds) {
        this.caSettlementFileIds = caSettlementFileIds;
    }

    public String getCaApplicantName() {
        return caApplicantName;
    }

    public void setCaApplicantName(String caApplicantName) {
        this.caApplicantName = caApplicantName;
    }

    public String getCaApplicantAbbName() {
        return caApplicantAbbName;
    }

    public void setCaApplicantAbbName(String caApplicantAbbName) {
        this.caApplicantAbbName = caApplicantAbbName;
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

    public List<CompletionAcceptanceCompanyDto> getCompAccepCompanys() {
        return compAccepCompanys;
    }

    public void setCompAccepCompanys(List<CompletionAcceptanceCompanyDto> compAccepCompanys) {
        this.compAccepCompanys = compAccepCompanys;
    }

    public List<CompletionAcceptanceSectionDto> getCompletionAcceptanceSections() {
        return completionAcceptanceSections;
    }

    public void setCompletionAcceptanceSections(List<CompletionAcceptanceSectionDto> completionAcceptanceSections) {
        this.completionAcceptanceSections = completionAcceptanceSections;
    }

    public List<CompletionAcceptanceContractDto> getCompletionAcceptanceContracts() {
        return completionAcceptanceContracts;
    }

    public void setCompletionAcceptanceContracts(List<CompletionAcceptanceContractDto> completionAcceptanceContracts) {
        this.completionAcceptanceContracts = completionAcceptanceContracts;
    }
}
