package com.jpmsoft.common.model.requestAndResponse.Project;

/**
 * 项目变更 项目建筑面积详情
 */
public class PcConstructionAreaDtlModel {

    private String catId;
    private String pcId;
    private String catName;
    private Float catArea;
    private String catRemark;
    private Integer catSort;
    private String catParentId;
    private Float catBuildArea;
    private Float catTotalArea;
    private Float catUpArea;
    private Float catDownArea;
    private Float catAreaHeight;
    private Integer catLayers;
    private String catDesc;

    public Float getCatBuildArea() {
        return catBuildArea;
    }

    public void setCatBuildArea(Float catBuildArea) {
        this.catBuildArea = catBuildArea;
    }

    public Float getCatTotalArea() {
        return catTotalArea;
    }

    public void setCatTotalArea(Float catTotalArea) {
        this.catTotalArea = catTotalArea;
    }

    public Float getCatUpArea() {
        return catUpArea;
    }

    public void setCatUpArea(Float catUpArea) {
        this.catUpArea = catUpArea;
    }
    public Float getCatDownArea() {
        return catDownArea;
    }

    public void setCatDownArea(Float catDownArea) {
        this.catDownArea = catDownArea;
    }
    public Float getCatAreaHeight() {
        return catAreaHeight;
    }

    public void setCatAreaHeight(Float catAreaHeight) { this.catAreaHeight = catAreaHeight; }

    public Integer getCatLayers() {
        return catLayers;
    }

    public void setCatLayers(Integer catLayers) { this.catLayers = catLayers; }
    public String getCatDesc() {
        return catDesc;
    }

    public void setCatDesc(String catDesc) {
        this.catDesc = catDesc;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getPcId() {
        return pcId;
    }

    public void setPcId(String pcId) {
        this.pcId = pcId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public Float getCatArea() {
        return catArea;
    }

    public void setCatArea(Float catArea) {
        this.catArea = catArea;
    }

    public String getCatRemark() {
        return catRemark;
    }

    public void setCatRemark(String catRemark) {
        this.catRemark = catRemark;
    }

    public Integer getCatSort() {
        return catSort;
    }

    public void setCatSort(Integer catSort) {
        this.catSort = catSort;
    }

    public String getCatParentId() {
        return catParentId;
    }

    public void setCatParentId(String catParentId) {
        this.catParentId = catParentId;
    }
}
