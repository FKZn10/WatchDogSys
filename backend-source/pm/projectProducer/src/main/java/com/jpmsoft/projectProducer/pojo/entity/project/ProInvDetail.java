package com.jpmsoft.projectProducer.pojo.entity.project;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目投资详情
 */
@TableName("Pro_Inv_Detail")
public class ProInvDetail {

    @TableId(type = IdType.INPUT)
    private String fsId;
    private String pId;
    private String fsCode;
    private Date fsCreateTime;
    private Float fsScale;
    private BigDecimal fsMoney;
    private String fsRemark;
    private Integer fsSort;

    public String getFsId() {
        return fsId;
    }

    public void setFsId(String fsId) {
        this.fsId = fsId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getFsCode() {
        return fsCode;
    }

    public void setFsCode(String fsCode) {
        this.fsCode = fsCode;
    }

    public Date getFsCreateTime() {
        return fsCreateTime;
    }

    public void setFsCreateTime(Date fsCreateTime) {
        this.fsCreateTime = fsCreateTime;
    }

    public Float getFsScale() {
        return fsScale;
    }

    public void setFsScale(Float fsScale) {
        this.fsScale = fsScale;
    }

    public BigDecimal getFsMoney() {
        return fsMoney;
    }

    public void setFsMoney(BigDecimal fsMoney) {
        this.fsMoney = fsMoney;
    }

    public String getFsRemark() {
        return fsRemark;
    }

    public void setFsRemark(String fsRemark) {
        this.fsRemark = fsRemark;
    }

    public Integer getFsSort() {
        return fsSort;
    }

    public void setFsSort(Integer fsSort) {
        this.fsSort = fsSort;
    }
}
