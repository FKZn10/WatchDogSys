package com.jpmsoft.common.model.requestAndResponse.SecInfo;


import java.util.Date;

/**
 * 专家选取记录 （招标代理）
 */
public class SecJGAgencySelectionRecordSimpleModel {

    private String SrId;
    //会议/咨询类型
    private String srType;
    //选取时间
    private Date srAccessTime;
    //会议事项简介
    private String srAbout;
    //会议开始时间
    private Date srStart;
    //选取人所属主管机构
    private String srCompetent;
    //咨询事项
    private String srItems;
    //资料清单
    private String srDataLists;
    //注意事项
    private String srNotes;
    //联系人
    private String srContact;
    //电话
    private String srPhone;
    private Integer state;
    private Date stateTime;
    private Date srCreateTime;
    private String srCreateUid;
    private String srCreateUname;
    //抽取人id
    private String extractUid;
    private String extractUname;
    /**
     * 专家选取申请ID
     */
    private String esId;
    private String teId;
    //技术类人员抽取申请专业详情（内部）
    private String srInsideExList;
    //技术类人员抽取申请专业详情（外部）
    private String srOutsideExList;
    //经济类人员抽取申请专业详情（外部）
    private String srOutsideEsList;
    //技术类专家总人数（内部）
    private Integer srInsideTechnologySum;
    //经济类专家总人数（内部）
    private Integer srInsideEconomicsSum;
    //技术类人数（内部）
    private Integer srInsideTechnologyNum;
    //经济类人数（内部）
    private Integer srInsideEconomicsNum;
    //专家总人数（内部）
    private Integer srInsideExpertNum;
    //技术类专家总人数（外部）
    private Integer srOutsideTechnologySum;
    //经济类专家总人数（外部）
    private Integer srOutsideEconomicsSum;
    //技术类人数（外部）
    private Integer srOutsideTechnologyNum;
    //经济类人数（外部）
    private Integer srOutsideEconomicsNum;
    //专家总人数（外部）
    private Integer srOutsideExpertNum;
    //技术类专家总人数
    private Integer srTechnologySum;
    //经济类专家总人数
    private Integer srEconomicsSum;
    //技术类人数
    private Integer srTechnologyNum;
    //经济类人数
    private Integer srEconomicsNum;
    //专家总人数
    private Integer srExpertNum;
    //开标时间
    private Date srBidTime;
    //开标地点
    private String srLocation;
    //申请单位
    private String srCompany;
    private String pId;
    private String secId;
    //使用方式
    private String srMode;
    //其他说明
    private String srOtherTxt;
    /**
     * 回避企业id
     */
    private String avoidCompanyId;
    /**
     * 回避企业名称
     */
    private String avoidCompanyName;

    /**
     * 备注
     */
    private String remark;

    public String getSrId() {
        return SrId;
    }

    public void setSrId(String srId) {
        SrId = srId;
    }

    public String getSrType() {
        return srType;
    }

    public void setSrType(String srType) {
        this.srType = srType;
    }

    public Date getSrAccessTime() {
        return srAccessTime;
    }

    public void setSrAccessTime(Date srAccessTime) {
        this.srAccessTime = srAccessTime;
    }

    public String getSrAbout() {
        return srAbout;
    }

    public void setSrAbout(String srAbout) {
        this.srAbout = srAbout;
    }

    public Date getSrStart() {
        return srStart;
    }

    public void setSrStart(Date srStart) {
        this.srStart = srStart;
    }

    public String getSrCompetent() {
        return srCompetent;
    }

    public void setSrCompetent(String srCompetent) {
        this.srCompetent = srCompetent;
    }

    public String getSrItems() {
        return srItems;
    }

    public void setSrItems(String srItems) {
        this.srItems = srItems;
    }

    public String getSrDataLists() {
        return srDataLists;
    }

    public void setSrDataLists(String srDataLists) {
        this.srDataLists = srDataLists;
    }

    public String getSrNotes() {
        return srNotes;
    }

    public void setSrNotes(String srNotes) {
        this.srNotes = srNotes;
    }

    public String getSrContact() {
        return srContact;
    }

    public void setSrContact(String srContact) {
        this.srContact = srContact;
    }

    public String getSrPhone() {
        return srPhone;
    }

    public void setSrPhone(String srPhone) {
        this.srPhone = srPhone;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getStateTime() {
        return stateTime;
    }

    public void setStateTime(Date stateTime) {
        this.stateTime = stateTime;
    }

    public Date getSrCreateTime() {
        return srCreateTime;
    }

    public void setSrCreateTime(Date srCreateTime) {
        this.srCreateTime = srCreateTime;
    }

    public String getSrCreateUid() {
        return srCreateUid;
    }

    public void setSrCreateUid(String srCreateUid) {
        this.srCreateUid = srCreateUid;
    }

    public String getSrCreateUname() {
        return srCreateUname;
    }

    public void setSrCreateUname(String srCreateUname) {
        this.srCreateUname = srCreateUname;
    }

    public String getExtractUid() {
        return extractUid;
    }

    public void setExtractUid(String extractUid) {
        this.extractUid = extractUid;
    }

    public String getExtractUname() {
        return extractUname;
    }

    public void setExtractUname(String extractUname) {
        this.extractUname = extractUname;
    }

    public String getEsId() {
        return esId;
    }

    public void setEsId(String esId) {
        this.esId = esId;
    }

    public String getTeId() {
        return teId;
    }

    public void setTeId(String teId) {
        this.teId = teId;
    }

    public String getSrInsideExList() {
        return srInsideExList;
    }

    public void setSrInsideExList(String srInsideExList) {
        this.srInsideExList = srInsideExList;
    }

    public String getSrOutsideExList() {
        return srOutsideExList;
    }

    public void setSrOutsideExList(String srOutsideExList) {
        this.srOutsideExList = srOutsideExList;
    }

    public String getSrOutsideEsList() {
        return srOutsideEsList;
    }

    public void setSrOutsideEsList(String srOutsideEsList) {
        this.srOutsideEsList = srOutsideEsList;
    }

    public Integer getSrInsideTechnologySum() {
        return srInsideTechnologySum;
    }

    public void setSrInsideTechnologySum(Integer srInsideTechnologySum) {
        this.srInsideTechnologySum = srInsideTechnologySum;
    }

    public Integer getSrInsideEconomicsSum() {
        return srInsideEconomicsSum;
    }

    public void setSrInsideEconomicsSum(Integer srInsideEconomicsSum) {
        this.srInsideEconomicsSum = srInsideEconomicsSum;
    }

    public Integer getSrInsideTechnologyNum() {
        return srInsideTechnologyNum;
    }

    public void setSrInsideTechnologyNum(Integer srInsideTechnologyNum) {
        this.srInsideTechnologyNum = srInsideTechnologyNum;
    }

    public Integer getSrInsideEconomicsNum() {
        return srInsideEconomicsNum;
    }

    public void setSrInsideEconomicsNum(Integer srInsideEconomicsNum) {
        this.srInsideEconomicsNum = srInsideEconomicsNum;
    }

    public Integer getSrInsideExpertNum() {
        return srInsideExpertNum;
    }

    public void setSrInsideExpertNum(Integer srInsideExpertNum) {
        this.srInsideExpertNum = srInsideExpertNum;
    }

    public Integer getSrOutsideTechnologySum() {
        return srOutsideTechnologySum;
    }

    public void setSrOutsideTechnologySum(Integer srOutsideTechnologySum) {
        this.srOutsideTechnologySum = srOutsideTechnologySum;
    }

    public Integer getSrOutsideEconomicsSum() {
        return srOutsideEconomicsSum;
    }

    public void setSrOutsideEconomicsSum(Integer srOutsideEconomicsSum) {
        this.srOutsideEconomicsSum = srOutsideEconomicsSum;
    }

    public Integer getSrOutsideTechnologyNum() {
        return srOutsideTechnologyNum;
    }

    public void setSrOutsideTechnologyNum(Integer srOutsideTechnologyNum) {
        this.srOutsideTechnologyNum = srOutsideTechnologyNum;
    }

    public Integer getSrOutsideEconomicsNum() {
        return srOutsideEconomicsNum;
    }

    public void setSrOutsideEconomicsNum(Integer srOutsideEconomicsNum) {
        this.srOutsideEconomicsNum = srOutsideEconomicsNum;
    }

    public Integer getSrOutsideExpertNum() {
        return srOutsideExpertNum;
    }

    public void setSrOutsideExpertNum(Integer srOutsideExpertNum) {
        this.srOutsideExpertNum = srOutsideExpertNum;
    }

    public Integer getSrTechnologySum() {
        return srTechnologySum;
    }

    public void setSrTechnologySum(Integer srTechnologySum) {
        this.srTechnologySum = srTechnologySum;
    }

    public Integer getSrEconomicsSum() {
        return srEconomicsSum;
    }

    public void setSrEconomicsSum(Integer srEconomicsSum) {
        this.srEconomicsSum = srEconomicsSum;
    }

    public Integer getSrTechnologyNum() {
        return srTechnologyNum;
    }

    public void setSrTechnologyNum(Integer srTechnologyNum) {
        this.srTechnologyNum = srTechnologyNum;
    }

    public Integer getSrEconomicsNum() {
        return srEconomicsNum;
    }

    public void setSrEconomicsNum(Integer srEconomicsNum) {
        this.srEconomicsNum = srEconomicsNum;
    }

    public Integer getSrExpertNum() {
        return srExpertNum;
    }

    public void setSrExpertNum(Integer srExpertNum) {
        this.srExpertNum = srExpertNum;
    }

    public Date getSrBidTime() {
        return srBidTime;
    }

    public void setSrBidTime(Date srBidTime) {
        this.srBidTime = srBidTime;
    }

    public String getSrLocation() {
        return srLocation;
    }

    public void setSrLocation(String srLocation) {
        this.srLocation = srLocation;
    }

    public String getSrCompany() {
        return srCompany;
    }

    public void setSrCompany(String srCompany) {
        this.srCompany = srCompany;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public String getSrMode() {
        return srMode;
    }

    public void setSrMode(String srMode) {
        this.srMode = srMode;
    }

    public String getSrOtherTxt() {
        return srOtherTxt;
    }

    public void setSrOtherTxt(String srOtherTxt) {
        this.srOtherTxt = srOtherTxt;
    }

    public String getAvoidCompanyId() {
        return avoidCompanyId;
    }

    public void setAvoidCompanyId(String avoidCompanyId) {
        this.avoidCompanyId = avoidCompanyId;
    }

    public String getAvoidCompanyName() {
        return avoidCompanyName;
    }

    public void setAvoidCompanyName(String avoidCompanyName) {
        this.avoidCompanyName = avoidCompanyName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
