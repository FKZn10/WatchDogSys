package com.jpmsoft.common.model.response.Oa;

import com.jpmsoft.common.model.ProjectCommonRes;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaProjectReportModel;

import java.util.Date;
import java.util.List;

/**
 * 工作报告（日报、周报、月报、季报、年报）
 */
public class OaWorkReportResponse extends ProjectCommonRes {

    private String wrId;
    private String pId;
    private String secId;
    private String conId;
    private String wrDId;
    private String wrCId;
    private String wrCode;
    private Date wrDate;
    private Date wrStartTime;
    private Date wrEndTime;
    private String wrTitle;
    private String wrContent;
    private String wrDoc;
    private String wrCreateUid;
    private String wrCreateUname;
    private Date wrCreateTime;
    private String wrType;
    private Date wrStateTime;
    private Integer wrState;
    private String wrProjectOverview;
    private String wrProjectSchedule;
    private Integer wrYear;
    private Integer wrQuarter;
    private Integer wrMonth;
    private String wrWeather;
    private String wrWindDirection;
    private Float wrWindMax;
    private Float wrWindMin;
    private Float wrWind;
    private Float wrTemperatureHigh;
    private Float wrTemperatureLow;
    private String wrContentQs;
    private String wrContentConstruct;
    private String wrContentOther;
    private String wrContractorId;

    private String conName;
    private String secName;
    private String wrDName;
    private String wrCName;
    private String wrCNameHistory;
    private String wrContractorName;
    private List<OaProjectReportModel> oaProReports;
    //单位简称
    private String wrCAbbName;
    private String wrContractorAbbName;

    /**
     * 子表 OA_Work_Report_Detail 的 Wrd_Number 数量合计（无子表记录时为 0）
     */
    private Integer wrdNumberTotal;


    /**
     *编辑方式：01-富文本编辑器;02-文档在线编辑
     */
    private String editType;

    /**
     *在线文档文件id
     */
    private String contentFile;
    /**
     *回复状态：1-已回复;2-未回复
     */
    private Integer replyState;

    /**
     * 01 日报
     * 02 周报
     * 03 月报
     */
    private String wrSign;

    /**
     * wrSign 的中文名称（日报/周报/月报）
     */
    public String getWrSignName() {
        if ("01".equals(wrSign)) return "监理日报";
        if ("02".equals(wrSign)) return "监理周报";
        if ("03".equals(wrSign)) return "监理月报";
        return "";
    }


    public String getWrSign() {
        return wrSign;
    }

    public void setWrSign(String wrSign) {
        this.wrSign = wrSign;
    }

    public String getContentFile() {
        return contentFile;
    }

    public void setContentFile(String contentFile) {
        this.contentFile = contentFile;
    }

    public String getEditType() {
        return editType;
    }

    public void setEditType(String editType) {
        this.editType = editType;
    }

    public String getWrId() {
        return wrId;
    }

    public void setWrId(String wrId) {
        this.wrId = wrId;
    }

    @Override
    public String getpId() {
        return pId;
    }

    @Override
    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getWrDId() {
        return wrDId;
    }

    public void setWrDId(String wrDId) {
        this.wrDId = wrDId;
    }

    public String getWrCId() {
        return wrCId;
    }

    public void setWrCId(String wrCId) {
        this.wrCId = wrCId;
    }

    public String getWrCode() {
        return wrCode;
    }

    public void setWrCode(String wrCode) {
        this.wrCode = wrCode;
    }

    public Date getWrDate() {
        return wrDate;
    }

    public void setWrDate(Date wrDate) {
        this.wrDate = wrDate;
    }

    public Date getWrStartTime() {
        return wrStartTime;
    }

    public void setWrStartTime(Date wrStartTime) {
        this.wrStartTime = wrStartTime;
    }

    public Date getWrEndTime() {
        return wrEndTime;
    }

    public void setWrEndTime(Date wrEndTime) {
        this.wrEndTime = wrEndTime;
    }

    public String getWrTitle() {
        return wrTitle;
    }

    public void setWrTitle(String wrTitle) {
        this.wrTitle = wrTitle;
    }

    public String getWrContent() {
        return wrContent;
    }

    public void setWrContent(String wrContent) {
        this.wrContent = wrContent;
    }

    public String getWrDoc() {
        return wrDoc;
    }

    public void setWrDoc(String wrDoc) {
        this.wrDoc = wrDoc;
    }

    public String getWrCreateUid() {
        return wrCreateUid;
    }

    public void setWrCreateUid(String wrCreateUid) {
        this.wrCreateUid = wrCreateUid;
    }

    public String getWrCreateUname() {
        return wrCreateUname;
    }

    public void setWrCreateUname(String wrCreateUname) {
        this.wrCreateUname = wrCreateUname;
    }

    public Date getWrCreateTime() {
        return wrCreateTime;
    }

    public void setWrCreateTime(Date wrCreateTime) {
        this.wrCreateTime = wrCreateTime;
    }

    public String getWrType() {
        return wrType;
    }

    public void setWrType(String wrType) {
        this.wrType = wrType;
    }

    public Date getWrStateTime() {
        return wrStateTime;
    }

    public void setWrStateTime(Date wrStateTime) {
        this.wrStateTime = wrStateTime;
    }

    public Integer getWrState() {
        return wrState;
    }

    public void setWrState(Integer wrState) {
        this.wrState = wrState;
    }

    public String getWrProjectOverview() {
        return wrProjectOverview;
    }

    public void setWrProjectOverview(String wrProjectOverview) {
        this.wrProjectOverview = wrProjectOverview;
    }

    public String getWrProjectSchedule() {
        return wrProjectSchedule;
    }

    public void setWrProjectSchedule(String wrProjectSchedule) {
        this.wrProjectSchedule = wrProjectSchedule;
    }

    public Integer getWrYear() {
        return wrYear;
    }

    public void setWrYear(Integer wrYear) {
        this.wrYear = wrYear;
    }

    public Integer getWrQuarter() {
        return wrQuarter;
    }

    public void setWrQuarter(Integer wrQuarter) {
        this.wrQuarter = wrQuarter;
    }

    public Integer getWrMonth() {
        return wrMonth;
    }

    public void setWrMonth(Integer wrMonth) {
        this.wrMonth = wrMonth;
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

    public String getWrDName() {
        return wrDName;
    }

    public void setWrDName(String wrDName) {
        this.wrDName = wrDName;
    }

    public String getWrCName() {
        return wrCName;
    }

    public void setWrCName(String wrCName) {
        this.wrCName = wrCName;
    }


    public String getWrCNameHistory() {
        return wrCNameHistory;
    }

    public void setWrCNameHistory(String wrCNameHistory) {
        this.wrCNameHistory = wrCNameHistory;
    }


    public List<OaProjectReportModel> getOaProReports() {
        return oaProReports;
    }

    public void setOaProReports(List<OaProjectReportModel> oaProReports) {
        this.oaProReports = oaProReports;
    }

    public String getWrWeather() {
        return wrWeather;
    }

    public void setWrWeather(String wrWeather) {
        this.wrWeather = wrWeather;
    }

    public String getWrWindDirection() {
        return wrWindDirection;
    }

    public void setWrWindDirection(String wrWindDirection) {
        this.wrWindDirection = wrWindDirection;
    }

    public Float getWrWindMax() {
        return wrWindMax;
    }

    public void setWrWindMax(Float wrWindMax) {
        this.wrWindMax = wrWindMax;
    }

    public Float getWrWindMin() {
        return wrWindMin;
    }

    public void setWrWindMin(Float wrWindMin) {
        this.wrWindMin = wrWindMin;
    }

    public Float getWrWind() {
        return wrWind;
    }

    public void setWrWind(Float wrWind) {
        this.wrWind = wrWind;
    }

    public Float getWrTemperatureHigh() {
        return wrTemperatureHigh;
    }

    public void setWrTemperatureHigh(Float wrTemperatureHigh) {
        this.wrTemperatureHigh = wrTemperatureHigh;
    }

    public Float getWrTemperatureLow() {
        return wrTemperatureLow;
    }

    public void setWrTemperatureLow(Float wrTemperatureLow) {
        this.wrTemperatureLow = wrTemperatureLow;
    }

    public String getWrContentQs() {
        return wrContentQs;
    }

    public void setWrContentQs(String wrContentQs) {
        this.wrContentQs = wrContentQs;
    }

    public String getWrContentConstruct() {
        return wrContentConstruct;
    }

    public void setWrContentConstruct(String wrContentConstruct) {
        this.wrContentConstruct = wrContentConstruct;
    }

    public String getWrContentOther() {
        return wrContentOther;
    }

    public void setWrContentOther(String wrContentOther) {
        this.wrContentOther = wrContentOther;
    }

    public String getWrContractorId() {
        return wrContractorId;
    }

    public void setWrContractorId(String wrContractorId) {
        this.wrContractorId = wrContractorId;
    }

    public String getWrContractorName() {
        return wrContractorName;
    }

    public void setWrContractorName(String wrContractorName) {
        this.wrContractorName = wrContractorName;
    }

    public String getWrCAbbName() {
        return wrCAbbName;
    }

    public void setWrCAbbName(String wrCAbbName) {
        this.wrCAbbName = wrCAbbName;
    }

    public String getWrContractorAbbName() {
        return wrContractorAbbName;
    }

    public void setWrContractorAbbName(String wrContractorAbbName) {
        this.wrContractorAbbName = wrContractorAbbName;
    }

    public Integer getReplyState() {
        return replyState;
    }

    public void setReplyState(Integer replyState) {
        this.replyState = replyState;
    }

    public Integer getWrdNumberTotal() {
        return wrdNumberTotal;
    }

    public void setWrdNumberTotal(Integer wrdNumberTotal) {
        this.wrdNumberTotal = wrdNumberTotal;
    }
}
