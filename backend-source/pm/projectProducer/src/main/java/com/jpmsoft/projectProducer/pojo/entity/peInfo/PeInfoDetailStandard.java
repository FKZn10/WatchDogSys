package com.jpmsoft.projectProducer.pojo.entity.peInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 履约评价标准
 */
@TableName("Pe_Info_Detail_Standard")
public class PeInfoDetailStandard {

    @TableId(type = IdType.INPUT)
    private String pedsId;
    private String pedId;
    private String pedsContent;
    private Float pedsScore;
    private Integer pedsOrder;
    private Integer pedsFlag;
    private String pedsRemark;

    public String getPedsId() {
        return pedsId;
    }

    public void setPedsId(String pedsId) {
        this.pedsId = pedsId;
    }

    public String getPedId() {
        return pedId;
    }

    public void setPedId(String pedId) {
        this.pedId = pedId;
    }

    public String getPedsContent() {
        return pedsContent;
    }

    public void setPedsContent(String pedsContent) {
        this.pedsContent = pedsContent;
    }

    public Float getPedsScore() {
        return pedsScore;
    }

    public void setPedsScore(Float pedsScore) {
        this.pedsScore = pedsScore;
    }

    public Integer getPedsOrder() {
        return pedsOrder;
    }

    public void setPedsOrder(Integer pedsOrder) {
        this.pedsOrder = pedsOrder;
    }

    public Integer getPedsFlag() {
        return pedsFlag;
    }

    public void setPedsFlag(Integer pedsFlag) {
        this.pedsFlag = pedsFlag;
    }

    public String getPedsRemark() {
        return pedsRemark;
    }

    public void setPedsRemark(String pedsRemark) {
        this.pedsRemark = pedsRemark;
    }
}
