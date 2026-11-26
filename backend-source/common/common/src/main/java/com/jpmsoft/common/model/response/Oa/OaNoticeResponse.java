package com.jpmsoft.common.model.response.Oa;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jpmsoft.common.model.ProjectCommonRes;

import java.util.Date;
import java.util.List;

/**
 * 通知公告
 */

public class OaNoticeResponse extends ProjectCommonRes {

    private String nId;
    private String nTitle;
    private String nCode;
    private String nType;
    private String nContent;
    private String nKeyword;
    private String nCreateUid;
    private String nCreateUname;
    private Integer nState;
    private String dId;
    private String pId;
    private String cId;
    private Date nCreateTime;
    /************************/
    private String dName;
    private String cName;
    private String cNameHistory;

    /**
     * 范围类型
     */
    private String rangeType;
    /**
     * 具体范围
     */
    private String specificScopeid;
    /**
     *
     */
    private String specificScopeName;
    /**
     * 优先程度
     */
    private String priority;
    //单位简称
    private String cAbbName;

    /**
     *办(科名称)
     */
    private String departmentName;

    /**
     *拟稿人
     */
    private String applyUserName;

    /**
     *线下发布地址
     */
    private String offlinePublishAddress;

    /**
     *线上发布方式(,分隔)；JPMSOFT_ONLINE_PUBLISH_TYPE
     */
    private String onlinePublishType;

    /**
     *编辑方式：01-富文本编辑器;02-文档在线编辑
     */
    private String editType;

    /**
     *在线文档文件id
     */
    private String contentFile;

    /**
     *通知类型(字典编码：JPMsoft_TZLX)
     */
    private String noticeType;

    private String depSpecificScopeName;
    private String depSpecificScopeid;
    private String projectSpecificScopeName;
    private String projectSpecificScopeid;
    private String userSpecificScopeName;
    private String userSpecificScopeid;

    /**
     *通知范围：项目组织机构名称
     */
    private String projectDepSpecificScopeName;

    /**
     *通知范围：项目组织机构id
     */
    private String projectDepSpecificScopeid;

    /**
     *项目组织机构所属的项目id
     */
    private String depProjectName;

    /**
     *项目组织机构所属的项目名称
     */
    private String depProjectId;

    /**
     * 置顶标志：1-否；0-是
     */
    private String topFlag;

    /**
     * 置顶日期至
     */
    private String topLimitDate;

    /**
     * 多项目组织机构
     */
    private List<OaNoticeProjectDeptResponse> projectDeptList;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date publishDate;

    /**
     * 是否添加文号
     */
    private String addSymbol;

    /**
     * 阅读状态：0-未读；1-已读（当前登录用户在 OA_Notice_Read 表中的状态）
     */
    private String isRead;

    public String getAddSymbol() {
        return addSymbol;
    }

    public void setAddSymbol(String addSymbol) {
        this.addSymbol = addSymbol;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public List<OaNoticeProjectDeptResponse> getProjectDeptList() {
        return projectDeptList;
    }

    public void setProjectDeptList(List<OaNoticeProjectDeptResponse> projectDeptList) {
        this.projectDeptList = projectDeptList;
    }

    public String getTopFlag() {
        return topFlag;
    }

    public void setTopFlag(String topFlag) {
        this.topFlag = topFlag;
    }

    public String getTopLimitDate() {
        return topLimitDate;
    }

    public void setTopLimitDate(String topLimitDate) {
        this.topLimitDate = topLimitDate;
    }

    public String getProjectDepSpecificScopeName() {
        return projectDepSpecificScopeName;
    }

    public void setProjectDepSpecificScopeName(String projectDepSpecificScopeName) {
        this.projectDepSpecificScopeName = projectDepSpecificScopeName;
    }

    public String getProjectDepSpecificScopeid() {
        return projectDepSpecificScopeid;
    }

    public void setProjectDepSpecificScopeid(String projectDepSpecificScopeid) {
        this.projectDepSpecificScopeid = projectDepSpecificScopeid;
    }

    public String getDepProjectName() {
        return depProjectName;
    }

    public void setDepProjectName(String depProjectName) {
        this.depProjectName = depProjectName;
    }

    public String getDepProjectId() {
        return depProjectId;
    }

    public void setDepProjectId(String depProjectId) {
        this.depProjectId = depProjectId;
    }

    public String getDepSpecificScopeName() {
        return depSpecificScopeName;
    }

    public void setDepSpecificScopeName(String depSpecificScopeName) {
        this.depSpecificScopeName = depSpecificScopeName;
    }

    public String getDepSpecificScopeid() {
        return depSpecificScopeid;
    }

    public void setDepSpecificScopeid(String depSpecificScopeid) {
        this.depSpecificScopeid = depSpecificScopeid;
    }

    public String getProjectSpecificScopeName() {
        return projectSpecificScopeName;
    }

    public void setProjectSpecificScopeName(String projectSpecificScopeName) {
        this.projectSpecificScopeName = projectSpecificScopeName;
    }

    public String getProjectSpecificScopeid() {
        return projectSpecificScopeid;
    }

    public void setProjectSpecificScopeid(String projectSpecificScopeid) {
        this.projectSpecificScopeid = projectSpecificScopeid;
    }

    public String getUserSpecificScopeName() {
        return userSpecificScopeName;
    }

    public void setUserSpecificScopeName(String userSpecificScopeName) {
        this.userSpecificScopeName = userSpecificScopeName;
    }

    public String getUserSpecificScopeid() {
        return userSpecificScopeid;
    }

    public void setUserSpecificScopeid(String userSpecificScopeid) {
        this.userSpecificScopeid = userSpecificScopeid;
    }

    public String getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
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

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getApplyUserName() {
        return applyUserName;
    }

    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName;
    }

    public String getOfflinePublishAddress() {
        return offlinePublishAddress;
    }

    public void setOfflinePublishAddress(String offlinePublishAddress) {
        this.offlinePublishAddress = offlinePublishAddress;
    }

    public String getOnlinePublishType() {
        return onlinePublishType;
    }

    public void setOnlinePublishType(String onlinePublishType) {
        this.onlinePublishType = onlinePublishType;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getRangeType() {
        return rangeType;
    }

    public void setRangeType(String rangeType) {
        this.rangeType = rangeType;
    }

    public String getSpecificScopeid() {
        return specificScopeid;
    }

    public void setSpecificScopeid(String specificScopeid) {
        this.specificScopeid = specificScopeid;
    }

    public String getSpecificScopeName() {
        return specificScopeName;
    }

    public void setSpecificScopeName(String specificScopeName) {
        this.specificScopeName = specificScopeName;
    }

    public String getnId() {
        return nId;
    }

    public void setnId(String nId) {
        this.nId = nId;
    }

    public String getnTitle() {
        return nTitle;
    }

    public void setnTitle(String nTitle) {
        this.nTitle = nTitle;
    }

    public String getnCode() {
        return nCode;
    }

    public void setnCode(String nCode) {
        this.nCode = nCode;
    }

    public String getnType() {
        return nType;
    }

    public void setnType(String nType) {
        this.nType = nType;
    }

    public String getnContent() {
        return nContent;
    }

    public void setnContent(String nContent) {
        this.nContent = nContent;
    }

    public String getnKeyword() {
        return nKeyword;
    }

    public void setnKeyword(String nKeyword) {
        this.nKeyword = nKeyword;
    }

    public String getnCreateUid() {
        return nCreateUid;
    }

    public void setnCreateUid(String nCreateUid) {
        this.nCreateUid = nCreateUid;
    }

    public String getnCreateUname() {
        return nCreateUname;
    }

    public void setnCreateUname(String nCreateUname) {
        this.nCreateUname = nCreateUname;
    }


    public Integer getnState() {
        return nState;
    }

    public void setnState(Integer nState) {
        this.nState = nState;
    }

    public String getdId() {
        return dId;
    }

    public void setdId(String dId) {
        this.dId = dId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public String getdName() {
        return dName;
    }

    public void setdName(String dName) {
        this.dName = dName;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }


    public Date getnCreateTime() {
        return nCreateTime;
    }

    public void setnCreateTime(Date nCreateTime) {
        this.nCreateTime = nCreateTime;
    }


    public String getcNameHistory() {
        return cNameHistory;
    }

    public void setcNameHistory(String cNameHistory) {
        this.cNameHistory = cNameHistory;
    }

    public String getcAbbName() {
        return cAbbName;
    }

    public void setcAbbName(String cAbbName) {
        this.cAbbName = cAbbName;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }
}
