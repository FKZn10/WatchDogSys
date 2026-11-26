package com.jpmsoft.common.model.requestAndResponse.Project;

/**
 * 规划房屋详细
 */
public class ProHouseDtlModel {

    private String houseId;
    private String pId;
    private Float houseArea;
    private Integer houseNumber;
    private String houseRemark;

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public Float getHouseArea() {
        return houseArea;
    }

    public void setHouseArea(Float houseArea) {
        this.houseArea = houseArea;
    }

    public Integer getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(Integer houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getHouseRemark() {
        return houseRemark;
    }

    public void setHouseRemark(String houseRemark) {
        this.houseRemark = houseRemark;
    }
}
