package com.jpmsoft.common.model.requestAndResponse.MeEnter;


import com.jpmsoft.common.model.BaseFileRequest;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 材料设备检测指引
 */
public class MeDetectionGuideModel extends BaseFileRequest {

    private String eadId;
    private String dgId;
    //检测类别
    private String dgType;
    //产品类别
    private String dgProductCategory;
    //产品名称 样品名称
    private String dgProduct;
    //检测项目
    private String dgItem;
    //取样数量
    private String dgCount;
    //检测周期
    private String dgCycle;
    //委托时间
    private String dgEntrustedTime;
    //检测依据
    private String dgTestBasis;
    //备注
    private String dgRemark;
    private String dgCreateUid;
    private String dgCreateUname;
    private Date dgCreateTime;
    private Integer dgState;
    private Date dgStateTime;
    //规格
    private String dgSpecification;
    //生产厂家
    private String dgManufacturer;
    //代表数量
    private BigDecimal dgRepresentativeQuantity;
    private String  dgRepresentativeQuantityType;
    //样品数量
    private Integer dgSampleQuantity;
    //取样地点
    private String dgSamplePlace;
    //使用部位
    private String dgUsePart;
    /**
     * 是否有检测报告
     * 0：全部
     * 1：有检测报告
     * 2：没有检测报告
     */
    private Integer haveReport;

    public String getEadId() {
        return eadId;
    }

    public void setEadId(String eadId) {
        this.eadId = eadId;
    }

    public String getDgId() {
        return dgId;
    }

    public void setDgId(String dgId) {
        this.dgId = dgId;
    }

    public String getDgType() {
        return dgType;
    }

    public void setDgType(String dgType) {
        this.dgType = dgType;
    }

    public String getDgProductCategory() {
        return dgProductCategory;
    }

    public void setDgProductCategory(String dgProductCategory) {
        this.dgProductCategory = dgProductCategory;
    }

    public String getDgProduct() {
        return dgProduct;
    }

    public void setDgProduct(String dgProduct) {
        this.dgProduct = dgProduct;
    }

    public String getDgItem() {
        return dgItem;
    }

    public void setDgItem(String dgItem) {
        this.dgItem = dgItem;
    }

    public String getDgCount() {
        return dgCount;
    }

    public void setDgCount(String dgCount) {
        this.dgCount = dgCount;
    }

    public String getDgCycle() {
        return dgCycle;
    }

    public void setDgCycle(String dgCycle) {
        this.dgCycle = dgCycle;
    }

    public String getDgEntrustedTime() {
        return dgEntrustedTime;
    }

    public void setDgEntrustedTime(String dgEntrustedTime) {
        this.dgEntrustedTime = dgEntrustedTime;
    }

    public String getDgTestBasis() {
        return dgTestBasis;
    }

    public void setDgTestBasis(String dgTestBasis) {
        this.dgTestBasis = dgTestBasis;
    }

    public String getDgRemark() {
        return dgRemark;
    }

    public void setDgRemark(String dgRemark) {
        this.dgRemark = dgRemark;
    }

    public String getDgCreateUid() {
        return dgCreateUid;
    }

    public void setDgCreateUid(String dgCreateUid) {
        this.dgCreateUid = dgCreateUid;
    }

    public String getDgCreateUname() {
        return dgCreateUname;
    }

    public void setDgCreateUname(String dgCreateUname) {
        this.dgCreateUname = dgCreateUname;
    }

    public Date getDgCreateTime() {
        return dgCreateTime;
    }

    public void setDgCreateTime(Date dgCreateTime) {
        this.dgCreateTime = dgCreateTime;
    }

    public Integer getDgState() {
        return dgState;
    }

    public void setDgState(Integer dgState) {
        this.dgState = dgState;
    }

    public Date getDgStateTime() {
        return dgStateTime;
    }

    public void setDgStateTime(Date dgStateTime) {
        this.dgStateTime = dgStateTime;
    }

    public String getDgSpecification() {
        return dgSpecification;
    }

    public void setDgSpecification(String dgSpecification) {
        this.dgSpecification = dgSpecification;
    }

    public String getDgManufacturer() {
        return dgManufacturer;
    }

    public void setDgManufacturer(String dgManufacturer) {
        this.dgManufacturer = dgManufacturer;
    }

    public BigDecimal getDgRepresentativeQuantity() {
        return dgRepresentativeQuantity;
    }

    public void setDgRepresentativeQuantity(BigDecimal dgRepresentativeQuantity) {
        this.dgRepresentativeQuantity = dgRepresentativeQuantity;
    }

    public Integer getDgSampleQuantity() {
        return dgSampleQuantity;
    }

    public void setDgSampleQuantity(Integer dgSampleQuantity) {
        this.dgSampleQuantity = dgSampleQuantity;
    }

    public String getDgSamplePlace() {
        return dgSamplePlace;
    }

    public void setDgSamplePlace(String dgSamplePlace) {
        this.dgSamplePlace = dgSamplePlace;
    }

    public String getDgUsePart() {
        return dgUsePart;
    }

    public void setDgUsePart(String dgUsePart) {
        this.dgUsePart = dgUsePart;
    }
    public Integer getHaveReport() {
        return haveReport;
    }
    public void setHaveReport(Integer haveReport) {
        this.haveReport = haveReport;
    }

    public String getDgRepresentativeQuantityType() {
        return dgRepresentativeQuantityType;
    }

    public void setDgRepresentativeQuantityType(String dgRepresentativeQuantityType) {
        this.dgRepresentativeQuantityType = dgRepresentativeQuantityType;
    }
}
