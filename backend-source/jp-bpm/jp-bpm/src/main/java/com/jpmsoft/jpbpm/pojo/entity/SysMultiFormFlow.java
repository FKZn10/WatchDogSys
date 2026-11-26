package com.jpmsoft.jpbpm.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("Sys_Multi_Form_Flow")
public class SysMultiFormFlow {
    @TableId(type = IdType.INPUT)
    private String mffId;
    private String mffMainFileId;
    private String mffFileId;
    private String tCode;
    private String mffTitle;
    private Integer mffNumber;
    private String teId;

    public String getMffId() {
        return mffId;
    }

    public void setMffId(String mffId) {
        this.mffId = mffId;
    }

    public String getMffMainFileId() {
        return mffMainFileId;
    }

    public void setMffMainFileId(String mffMainFileId) {
        this.mffMainFileId = mffMainFileId;
    }

    public String getMffFileId() {
        return mffFileId;
    }

    public void setMffFileId(String mffFileId) {
        this.mffFileId = mffFileId;
    }

    public String gettCode() {
        return tCode;
    }

    public void settCode(String tCode) {
        this.tCode = tCode;
    }

    public String getMffTitle() {
        return mffTitle;
    }

    public void setMffTitle(String mffTitle) {
        this.mffTitle = mffTitle;
    }

    public Integer getMffNumber() {
        return mffNumber;
    }

    public void setMffNumber(Integer mffNumber) {
        this.mffNumber = mffNumber;
    }

    public String getTeId() {
        return teId;
    }

    public void setTeId(String teId) {
        this.teId = teId;
    }
}
