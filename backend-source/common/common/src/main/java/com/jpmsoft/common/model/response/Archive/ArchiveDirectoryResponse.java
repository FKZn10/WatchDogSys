package com.jpmsoft.common.model.response.Archive;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 档案目录
 */

public class ArchiveDirectoryResponse implements Serializable {

    private static final long serialVersionUID = -1363966369269523634L;
    private String adId;
    private String adName;
    private String adCode;
    private String adPersonUid;
    private String adPersonUname;
    private Integer adOrder;
    private String adNo;
    private String adRpBuild;
    private String adRpConstruction;
    private String adRpDesign;
    private String adRpSupervisor;
    private String adRpCity;
    private String adCreateUid;
    private String adCreateUname;
    private Date adCreateTime;
    private String adVersion;
    private Integer adState;
    private String adParentId;
    private String adRemark;
    private String adLink;
    private String adType;
    /***************/
    private String adRpBuildName;
    private String adRpConstructionName;
    private String adRpDesignName;
    private String adRpSupervisorName;
    private String adRpBuildAbbName;
    private String adRpConstructionAbbName;
    private String adRpDesignAbbName;
    private String adRpSupervisorAbbName;
    private Integer archived;
    private Integer notArchived;
    private Integer childCount;
    private List<String> powerCodes;
    private List<ArchiveDirectoryResponse> childNodes;

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public String getAdName() {
        return adName;
    }

    public void setAdName(String adName) {
        this.adName = adName;
    }

    public String getAdCode() {
        return adCode;
    }

    public void setAdCode(String adCode) {
        this.adCode = adCode;
    }

    public String getAdPersonUid() {
        return adPersonUid;
    }

    public void setAdPersonUid(String adPersonUid) {
        this.adPersonUid = adPersonUid;
    }

    public String getAdPersonUname() {
        return adPersonUname;
    }

    public void setAdPersonUname(String adPersonUname) {
        this.adPersonUname = adPersonUname;
    }

    public Integer getAdOrder() {
        return adOrder;
    }

    public void setAdOrder(Integer adOrder) {
        this.adOrder = adOrder;
    }

    public String getAdNo() {
        return adNo;
    }

    public void setAdNo(String adNo) {
        this.adNo = adNo;
    }

    public String getAdRpBuild() {
        return adRpBuild;
    }

    public void setAdRpBuild(String adRpBuild) {
        this.adRpBuild = adRpBuild;
    }

    public String getAdRpConstruction() {
        return adRpConstruction;
    }

    public void setAdRpConstruction(String adRpConstruction) {
        this.adRpConstruction = adRpConstruction;
    }

    public String getAdRpDesign() {
        return adRpDesign;
    }

    public void setAdRpDesign(String adRpDesign) {
        this.adRpDesign = adRpDesign;
    }

    public String getAdRpSupervisor() {
        return adRpSupervisor;
    }

    public void setAdRpSupervisor(String adRpSupervisor) {
        this.adRpSupervisor = adRpSupervisor;
    }

    public String getAdRpCity() {
        return adRpCity;
    }

    public void setAdRpCity(String adRpCity) {
        this.adRpCity = adRpCity;
    }

    public String getAdCreateUid() {
        return adCreateUid;
    }

    public void setAdCreateUid(String adCreateUid) {
        this.adCreateUid = adCreateUid;
    }

    public String getAdCreateUname() {
        return adCreateUname;
    }

    public void setAdCreateUname(String adCreateUname) {
        this.adCreateUname = adCreateUname;
    }

    public Date getAdCreateTime() {
        return adCreateTime;
    }

    public void setAdCreateTime(Date adCreateTime) {
        this.adCreateTime = adCreateTime;
    }

    public String getAdVersion() {
        return adVersion;
    }

    public void setAdVersion(String adVersion) {
        this.adVersion = adVersion;
    }

    public Integer getAdState() {
        return adState;
    }

    public void setAdState(Integer adState) {
        this.adState = adState;
    }

    public String getAdParentId() {
        return adParentId;
    }

    public void setAdParentId(String adParentId) {
        this.adParentId = adParentId;
    }

    public String getAdRemark() {
        return adRemark;
    }

    public void setAdRemark(String adRemark) {
        this.adRemark = adRemark;
    }

    public String getAdLink() {
        return adLink;
    }

    public void setAdLink(String adLink) {
        this.adLink = adLink;
    }

    public String getAdType() {
        return adType;
    }

    public void setAdType(String adType) {
        this.adType = adType;
    }

    public String getAdRpBuildName() {
        return adRpBuildName;
    }

    public void setAdRpBuildName(String adRpBuildName) {
        this.adRpBuildName = adRpBuildName;
    }

    public String getAdRpConstructionName() {
        return adRpConstructionName;
    }

    public void setAdRpConstructionName(String adRpConstructionName) {
        this.adRpConstructionName = adRpConstructionName;
    }

    public String getAdRpDesignName() {
        return adRpDesignName;
    }

    public void setAdRpDesignName(String adRpDesignName) {
        this.adRpDesignName = adRpDesignName;
    }

    public String getAdRpSupervisorName() {
        return adRpSupervisorName;
    }

    public void setAdRpSupervisorName(String adRpSupervisorName) {
        this.adRpSupervisorName = adRpSupervisorName;
    }

    public String getAdRpBuildAbbName() {
        return adRpBuildAbbName;
    }

    public void setAdRpBuildAbbName(String adRpBuildAbbName) {
        this.adRpBuildAbbName = adRpBuildAbbName;
    }

    public String getAdRpConstructionAbbName() {
        return adRpConstructionAbbName;
    }

    public void setAdRpConstructionAbbName(String adRpConstructionAbbName) {
        this.adRpConstructionAbbName = adRpConstructionAbbName;
    }

    public String getAdRpDesignAbbName() {
        return adRpDesignAbbName;
    }

    public void setAdRpDesignAbbName(String adRpDesignAbbName) {
        this.adRpDesignAbbName = adRpDesignAbbName;
    }

    public String getAdRpSupervisorAbbName() {
        return adRpSupervisorAbbName;
    }

    public void setAdRpSupervisorAbbName(String adRpSupervisorAbbName) {
        this.adRpSupervisorAbbName = adRpSupervisorAbbName;
    }

    public Integer getArchived() {
        return archived;
    }

    public void setArchived(Integer archived) {
        this.archived = archived;
    }

    public Integer getNotArchived() {
        return notArchived;
    }

    public void setNotArchived(Integer notArchived) {
        this.notArchived = notArchived;
    }

    public Integer getChildCount() {
        return childCount;
    }

    public void setChildCount(Integer childCount) {
        this.childCount = childCount;
    }

    public List<String> getPowerCodes() {
        return powerCodes;
    }

    public void setPowerCodes(List<String> powerCodes) {
        this.powerCodes = powerCodes;
    }

    public List<ArchiveDirectoryResponse> getChildNodes() {
        return childNodes;
    }

    public void setChildNodes(List<ArchiveDirectoryResponse> childNodes) {
        this.childNodes = childNodes;
    }


    // 递归方法，用于统计包括所有子节点在内的archived和notArchived值，并回写到当前节点
    public void calculateAndSetTotalArchivedAndNotArchived() {
        // 初始化当前节点的统计值为自身的值
        int totalArchived = this.archived;
        int totalNotArchived = this.notArchived;
        // 遍历子节点
        for (ArchiveDirectoryResponse child : childNodes) {
            // 递归调用以计算子节点的统计值
            child.calculateAndSetTotalArchivedAndNotArchived();
            // 累加子节点的统计值到当前节点
            totalArchived += child.getArchived();
            totalNotArchived += child.getNotArchived();
        }
        // 将统计值回写到当前节点
        this.setArchived(totalArchived);
        this.setNotArchived(totalNotArchived);
    }
}
