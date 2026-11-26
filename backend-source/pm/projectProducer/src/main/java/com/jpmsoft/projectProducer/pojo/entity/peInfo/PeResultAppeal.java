package com.jpmsoft.projectProducer.pojo.entity.peInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * 履约评价结果送达
 */
@TableName("Pe_Result_Appeal")
public class PeResultAppeal {

    @TableId(type = IdType.INPUT)
    private String raId;
    private String rId;
    private String raRemark;
    private String raCreateUid;
    private String raCreateUname;
    private Date raCreateTime;
    private String teId;

    public String getRaId() {
        return raId;
    }

    public void setRaId(String raId) {
        this.raId = raId;
    }

    public String getrId() {
        return rId;
    }

    public void setrId(String rId) {
        this.rId = rId;
    }

    public String getRaRemark() {
        return raRemark;
    }

    public void setRaRemark(String raRemark) {
        this.raRemark = raRemark;
    }

    public String getRaCreateUid() {
        return raCreateUid;
    }

    public void setRaCreateUid(String raCreateUid) {
        this.raCreateUid = raCreateUid;
    }

    public String getRaCreateUname() {
        return raCreateUname;
    }

    public void setRaCreateUname(String raCreateUname) {
        this.raCreateUname = raCreateUname;
    }

    public Date getRaCreateTime() {
        return raCreateTime;
    }

    public void setRaCreateTime(Date raCreateTime) {
        this.raCreateTime = raCreateTime;
    }

    public String getTeId() {
        return teId;
    }

    public void setTeId(String teId) {
        this.teId = teId;
    }
}
