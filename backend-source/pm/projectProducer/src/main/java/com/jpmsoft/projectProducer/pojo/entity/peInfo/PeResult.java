package com.jpmsoft.projectProducer.pojo.entity.peInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * 履约评价结果送达
 */
@TableName("Pe_Result")
public class PeResult {
    @TableId(type = IdType.INPUT)
    private String rId;
    //考核评分记录ID
    private String peId;
    //送达时间（短信发送时间）
    private Date rDeliveryTime;
    //送达状态
    private Integer rDeliveryStatus;
    //签收人Id
    private String rSignUid;
    //签收人姓名
    private String rSignUname;
    //签收时间
    private Date rSignTime;
    //签收状态
    private Integer rSignState;
    //签收内容
    private String rSignContent;

    public String getrId() {
        return rId;
    }

    public void setrId(String rId) {
        this.rId = rId;
    }

    public String getPeId() {
        return peId;
    }

    public void setPeId(String peId) {
        this.peId = peId;
    }

    public Date getrDeliveryTime() {
        return rDeliveryTime;
    }

    public void setrDeliveryTime(Date rDeliveryTime) {
        this.rDeliveryTime = rDeliveryTime;
    }

    public Integer getrDeliveryStatus() {
        return rDeliveryStatus;
    }

    public void setrDeliveryStatus(Integer rDeliveryStatus) {
        this.rDeliveryStatus = rDeliveryStatus;
    }

    public String getrSignUid() {
        return rSignUid;
    }

    public void setrSignUid(String rSignUid) {
        this.rSignUid = rSignUid;
    }

    public String getrSignUname() {
        return rSignUname;
    }

    public void setrSignUname(String rSignUname) {
        this.rSignUname = rSignUname;
    }

    public Date getrSignTime() {
        return rSignTime;
    }

    public void setrSignTime(Date rSignTime) {
        this.rSignTime = rSignTime;
    }

    public Integer getrSignState() {
        return rSignState;
    }

    public void setrSignState(Integer rSignState) {
        this.rSignState = rSignState;
    }

    public String getrSignContent() {
        return rSignContent;
    }

    public void setrSignContent(String rSignContent) {
        this.rSignContent = rSignContent;
    }
}
