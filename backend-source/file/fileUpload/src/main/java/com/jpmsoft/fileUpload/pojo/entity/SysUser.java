package com.jpmsoft.fileUpload.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("SYS_User")
public class SysUser {
    @TableId(type = IdType.INPUT)
    private String uId;
    private String plId;
    private String cId;
    private String uName;
    private String uCode;
    private String uMtel;
    private String uTel;
    private String uEmail;
    private String uAccount;
    private String uPassword;
    private String uSex;
    private Date uBirthday;
    private String uState;
    private String uMdmNo;
    private Date uCreatetime;
    private String uCreateuId;
    private String uCreateuName;
    private String uType;
    private String teId;

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getPlId() {
        return plId;
    }

    public void setPlId(String plId) {
        this.plId = plId;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuCode() {
        return uCode;
    }

    public void setuCode(String uCode) {
        this.uCode = uCode;
    }

    public String getuMtel() {
        return uMtel;
    }

    public void setuMtel(String uMtel) {
        this.uMtel = uMtel;
    }

    public String getuTel() {
        return uTel;
    }

    public void setuTel(String uTel) {
        this.uTel = uTel;
    }

    public String getuEmail() {
        return uEmail;
    }

    public void setuEmail(String uEmail) {
        this.uEmail = uEmail;
    }

    public String getuAccount() {
        return uAccount;
    }

    public void setuAccount(String uAccount) {
        this.uAccount = uAccount;
    }

    public String getuPassword() {
        return uPassword;
    }

    public void setuPassword(String uPassword) {
        this.uPassword = uPassword;
    }

    public String getuSex() {
        return uSex;
    }

    public void setuSex(String uSex) {
        this.uSex = uSex;
    }

    public Date getuBirthday() {
        return uBirthday;
    }

    public void setuBirthday(Date uBirthday) {
        this.uBirthday = uBirthday;
    }

    public String getuState() {
        return uState;
    }

    public void setuState(String uState) {
        this.uState = uState;
    }

    public String getuMdmNo() {
        return uMdmNo;
    }

    public void setuMdmNo(String uMdmNo) {
        this.uMdmNo = uMdmNo;
    }

    public Date getuCreatetime() {
        return uCreatetime;
    }

    public void setuCreatetime(Date uCreatetime) {
        this.uCreatetime = uCreatetime;
    }

    public String getuCreateuId() {
        return uCreateuId;
    }

    public void setuCreateuId(String uCreateuId) {
        this.uCreateuId = uCreateuId;
    }

    public String getuCreateuName() {
        return uCreateuName;
    }

    public void setuCreateuName(String uCreateuName) {
        this.uCreateuName = uCreateuName;
    }

    public String getuType() {
        return uType;
    }

    public void setuType(String uType) {
        this.uType = uType;
    }

    public String getTeId() {
        return teId;
    }

    public void setTeId(String teId) {
        this.teId = teId;
    }
}
