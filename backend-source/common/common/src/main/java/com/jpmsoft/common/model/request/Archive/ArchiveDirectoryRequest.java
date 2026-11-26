package com.jpmsoft.common.model.request.Archive;

import com.jpmsoft.common.model.BaseModeRequest;

import java.util.Date;
import java.util.List;

/**
 * 档案目录
 */

public class ArchiveDirectoryRequest extends BaseModeRequest {


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

    private String pId;
    /**
     * 是否过滤掉未归档的目录
     */
    private Boolean filterUnArchive;
    private List<ArchiveFiletemplateRequest> archiveFiletemplates;


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

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public Boolean getFilterUnArchive() {
        return filterUnArchive;
    }

    public void setFilterUnArchive(Boolean filterUnArchive) {
        this.filterUnArchive = filterUnArchive;
    }

    public List<ArchiveFiletemplateRequest> getArchiveFiletemplates() {
        return archiveFiletemplates;
    }

    public void setArchiveFiletemplates(List<ArchiveFiletemplateRequest> archiveFiletemplates) {
        this.archiveFiletemplates = archiveFiletemplates;
    }
}
