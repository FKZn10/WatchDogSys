package com.jpmsoft.userProducer.pojo.dto;

import com.jpmsoft.common.model.BaseModeRequest;
import com.jpmsoft.common.model.request.SysExtendConfDTO;
import com.jpmsoft.common.model.response.SysFileTemplateDtlDTO;
import com.jpmsoft.userProducer.constants.BaseMsg;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class FileTemplateDto extends BaseModeRequest implements Serializable {

    private static final long serialVersionUID = 7412924558257309175L;
    public String tId;
    public String tName;
    public String tCode;
    public String tFlowpage;
    public String tInfopage;
    public String tUpdatepage;
    public String tPrintpage;
    public String tSystem;
    public String tGeneral;
    public String tWordpage;
    public String tExcelpage;
    public String tReportpage;
    public String tCoordinationpage;
    public String tType;
    public Date tCreatetime;
    public String tCreateuser;
    public String tCreateusername;
    public String tNeedflow;
    public String tAllowdraft;
    public String tContent;
    public String tDescription;
    public String tHelper;
    public String tTable;
    public String tParentid;
    public Integer tSort;
    public String tStats;
    public String tSolution;
    public String tClient;
    private String tPrimaryKey;
    private String tTaskName;
    private String tAppFlowpage;
    private String tAppInfopage;
    private String tAppUpdatepage;
    
    @NotBlank(message = BaseMsg.noOperationTypeSelected)
    public String type;

    //流程字段
    private String tStateKey;
    private String tStateTimeKey;
    private String tFileTips;

    //表单设计字段
    private String tListRequestUrl;
    private String tListRequestType;
    private String tListRequestParameter;
    private String tDetailsRequestUrl;
    private String tDetailsRequestType;
    private String tDetailsRequestParameter;
    private String tCreateRequestUrl;
    private String tCreateRequestType;
    private String tCreateRequestParameter;
    private String tColumnContent;
    private String tPageSize;
    private String tSlotTemplate;
    private Boolean tIsArchivists;
    private String tTableSet;
    private String tIsDraft;
    private String adId;

    private List<SysExtendConfDTO> sysExtendConfs;

    private List<SysFileTemplateDtlDTO> fileTemplateDtls;

    private Date updateTime;
    private String updateUid;
    private String updateUserName;

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUid() {
        return updateUid;
    }

    public void setUpdateUid(String updateUid) {
        this.updateUid = updateUid;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public List<SysFileTemplateDtlDTO> getFileTemplateDtls() {
        return fileTemplateDtls;
    }

    public void setFileTemplateDtls(List<SysFileTemplateDtlDTO> fileTemplateDtls) {
        this.fileTemplateDtls = fileTemplateDtls;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String gettId() {
        return tId;
    }

    public void settId(String tId) {
        this.tId = tId;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public String gettCode() {
        return tCode;
    }

    public void settCode(String tCode) {
        this.tCode = tCode;
    }

    public String gettFlowpage() {
        return tFlowpage;
    }

    public void settFlowpage(String tFlowpage) {
        this.tFlowpage = tFlowpage;
    }

    public String gettInfopage() {
        return tInfopage;
    }

    public void settInfopage(String tInfopage) {
        this.tInfopage = tInfopage;
    }

    public String gettUpdatepage() {
        return tUpdatepage;
    }

    public void settUpdatepage(String tUpdatepage) {
        this.tUpdatepage = tUpdatepage;
    }

    public String gettPrintpage() {
        return tPrintpage;
    }

    public void settPrintpage(String tPrintpage) {
        this.tPrintpage = tPrintpage;
    }

    public String gettSystem() {
        return tSystem;
    }

    public void settSystem(String tSystem) {
        this.tSystem = tSystem;
    }

    public String gettGeneral() {
        return tGeneral;
    }

    public void settGeneral(String tGeneral) {
        this.tGeneral = tGeneral;
    }

    public String gettWordpage() {
        return tWordpage;
    }

    public void settWordpage(String tWordpage) {
        this.tWordpage = tWordpage;
    }

    public String gettExcelpage() {
        return tExcelpage;
    }

    public void settExcelpage(String tExcelpage) {
        this.tExcelpage = tExcelpage;
    }

    public String gettReportpage() {
        return tReportpage;
    }

    public void settReportpage(String tReportpage) {
        this.tReportpage = tReportpage;
    }

    public String gettCoordinationpage() {
        return tCoordinationpage;
    }

    public void settCoordinationpage(String tCoordinationpage) {
        this.tCoordinationpage = tCoordinationpage;
    }

    public String gettType() {
        return tType;
    }

    public void settType(String tType) {
        this.tType = tType;
    }

    public Date gettCreatetime() {
        return tCreatetime;
    }

    public void settCreatetime(Date tCreatetime) {
        this.tCreatetime = tCreatetime;
    }

    public String gettCreateuser() {
        return tCreateuser;
    }

    public void settCreateuser(String tCreateuser) {
        this.tCreateuser = tCreateuser;
    }

    public String gettCreateusername() {
        return tCreateusername;
    }

    public void settCreateusername(String tCreateusername) {
        this.tCreateusername = tCreateusername;
    }

    public String gettNeedflow() {
        return tNeedflow;
    }

    public void settNeedflow(String tNeedflow) {
        this.tNeedflow = tNeedflow;
    }

    public String gettAllowdraft() {
        return tAllowdraft;
    }

    public void settAllowdraft(String tAllowdraft) {
        this.tAllowdraft = tAllowdraft;
    }

    public String gettContent() {
        return tContent;
    }

    public void settContent(String tContent) {
        this.tContent = tContent;
    }

    public String gettDescription() {
        return tDescription;
    }

    public void settDescription(String tDescription) {
        this.tDescription = tDescription;
    }

    public String gettHelper() {
        return tHelper;
    }

    public void settHelper(String tHelper) {
        this.tHelper = tHelper;
    }

    public String gettTable() {
        return tTable;
    }

    public void settTable(String tTable) {
        this.tTable = tTable;
    }

    public String gettParentid() {
        return tParentid;
    }

    public void settParentid(String tParentid) {
        this.tParentid = tParentid;
    }

    public Integer gettSort() {
        return tSort;
    }

    public void settSort(Integer tSort) {
        this.tSort = tSort;
    }

    public String gettStats() {
        return tStats;
    }

    public void settStats(String tStats) {
        this.tStats = tStats;
    }

    public String gettSolution() {
        return tSolution;
    }

    public void settSolution(String tSolution) {
        this.tSolution = tSolution;
    }

    public String gettPrimaryKey() {
        return tPrimaryKey;
    }

    public void settPrimaryKey(String tPrimaryKey) {
        this.tPrimaryKey = tPrimaryKey;
    }

    public String gettClient() {
        return tClient;
    }

    public void settClient(String tClient) {
        this.tClient = tClient;
    }

    public String gettStateKey() {
        return tStateKey;
    }

    public void settStateKey(String tStateKey) {
        this.tStateKey = tStateKey;
    }

    public String gettStateTimeKey() {
        return tStateTimeKey;
    }

    public void settStateTimeKey(String tStateTimeKey) {
        this.tStateTimeKey = tStateTimeKey;
    }

    public String gettFileTips() {
        return tFileTips;
    }

    public void settFileTips(String tFileTips) {
        this.tFileTips = tFileTips;
    }

    public String gettListRequestUrl() {
        return tListRequestUrl;
    }

    public void settListRequestUrl(String tListRequestUrl) {
        this.tListRequestUrl = tListRequestUrl;
    }

    public String gettListRequestType() {
        return tListRequestType;
    }

    public void settListRequestType(String tListRequestType) {
        this.tListRequestType = tListRequestType;
    }

    public String gettListRequestParameter() {
        return tListRequestParameter;
    }

    public void settListRequestParameter(String tListRequestParameter) {
        this.tListRequestParameter = tListRequestParameter;
    }

    public String gettDetailsRequestUrl() {
        return tDetailsRequestUrl;
    }

    public void settDetailsRequestUrl(String tDetailsRequestUrl) {
        this.tDetailsRequestUrl = tDetailsRequestUrl;
    }

    public String gettDetailsRequestType() {
        return tDetailsRequestType;
    }

    public void settDetailsRequestType(String tDetailsRequestType) {
        this.tDetailsRequestType = tDetailsRequestType;
    }

    public String gettDetailsRequestParameter() {
        return tDetailsRequestParameter;
    }

    public void settDetailsRequestParameter(String tDetailsRequestParameter) {
        this.tDetailsRequestParameter = tDetailsRequestParameter;
    }

    public String gettCreateRequestUrl() {
        return tCreateRequestUrl;
    }

    public void settCreateRequestUrl(String tCreateRequestUrl) {
        this.tCreateRequestUrl = tCreateRequestUrl;
    }

    public String gettCreateRequestType() {
        return tCreateRequestType;
    }

    public void settCreateRequestType(String tCreateRequestType) {
        this.tCreateRequestType = tCreateRequestType;
    }

    public String gettCreateRequestParameter() {
        return tCreateRequestParameter;
    }

    public void settCreateRequestParameter(String tCreateRequestParameter) {
        this.tCreateRequestParameter = tCreateRequestParameter;
    }

    public String gettColumnContent() {
        return tColumnContent;
    }

    public void settColumnContent(String tColumnContent) {
        this.tColumnContent = tColumnContent;
    }

    public String gettPageSize() {
        return tPageSize;
    }

    public void settPageSize(String tPageSize) {
        this.tPageSize = tPageSize;
    }

    public String gettSlotTemplate() {
        return tSlotTemplate;
    }

    public void settSlotTemplate(String tSlotTemplate) {
        this.tSlotTemplate = tSlotTemplate;
    }

    public Boolean gettIsArchivists() {
        return tIsArchivists;
    }

    public void settIsArchivists(Boolean tIsArchivists) {
        this.tIsArchivists = tIsArchivists;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String gettTaskName() {
        return tTaskName;
    }

    public void settTaskName(String tTaskName) {
        this.tTaskName = tTaskName;
    }

    public String gettAppFlowpage() {
        return tAppFlowpage;
    }

    public void settAppFlowpage(String tAppFlowpage) {
        this.tAppFlowpage = tAppFlowpage;
    }

    public String gettAppInfopage() {
        return tAppInfopage;
    }

    public void settAppInfopage(String tAppInfopage) {
        this.tAppInfopage = tAppInfopage;
    }

    public String gettAppUpdatepage() {
        return tAppUpdatepage;
    }

    public void settAppUpdatepage(String tAppUpdatepage) {
        this.tAppUpdatepage = tAppUpdatepage;
    }

    public List<SysExtendConfDTO> getSysExtendConfs() {
        return sysExtendConfs;
    }

    public void setSysExtendConfs(List<SysExtendConfDTO> sysExtendConfs) {
        this.sysExtendConfs = sysExtendConfs;
    }

    public String gettTableSet() {
        return tTableSet;
    }

    public void settTableSet(String tTableSet) {
        this.tTableSet = tTableSet;
    }

    public String gettIsDraft() {
        return tIsDraft;
    }

    public void settIsDraft(String tIsDraft) {
        this.tIsDraft = tIsDraft;
    }

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }
}
