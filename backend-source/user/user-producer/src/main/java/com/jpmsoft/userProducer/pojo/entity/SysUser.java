package com.jpmsoft.userProducer.pojo.entity;

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
    private String uOffice;
    private String uCornet;
    private String uSalt;
    private String uIdcard;
    private Integer uFirstLogin;
    private Integer uCheckSms;
    private Date uPassUpdateTime;
    private Integer uSort;
    private String uPushId;
    private String openId;
    private String uSignature;
    private String uQualification;
    /**
     * 是否越过校验超期禁用标识
     * 开启了超时禁用账户配置  后台启用该账户 下次单次登录直接越过超时禁用判断（一次性）
     * true 越过校验  false不越过校验
     */
    private Boolean uAutoDisable;
    /**
     * 租户ID
     */
    private String teId;
    /**
     * 身份证明文
     * 不对外仅用于内部逻辑
     */
    private String uPlaintextNum;
    /**
     * 用户类别
     * 借调用户
     */
    private String uClass;
    /**
     * ukey设备密钥
     */
    private String uKey;
    /**
     * ukey设备公钥
     */
    private String uKeyPubKey;
    private String sign;
    /**
     * 手机号加密
     */
    private String uPhone;

    private String qzOpenId;
    private String qzBizNo;

    public String getQzOpenId() {
        return qzOpenId;
    }

    public void setQzOpenId(String qzOpenId) {
        this.qzOpenId = qzOpenId;
    }

    public String getQzBizNo() {
        return qzBizNo;
    }

    public void setQzBizNo(String qzBizNo) {
        this.qzBizNo = qzBizNo;
    }

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

    public String getuOffice() {
        return uOffice;
    }

    public void setuOffice(String uOffice) {
        this.uOffice = uOffice;
    }

    public String getuCornet() {
        return uCornet;
    }

    public void setuCornet(String uCornet) {
        this.uCornet = uCornet;
    }

    public String getuSalt() {
        return uSalt;
    }

    public void setuSalt(String uSalt) {
        this.uSalt = uSalt;
    }

    public String getuIdcard() {
        return uIdcard;
    }

    public void setuIdcard(String uIdcard) {
        this.uIdcard = uIdcard;
    }

    public Integer getuFirstLogin() {
        return uFirstLogin;
    }

    public void setuFirstLogin(Integer uFirstLogin) {
        this.uFirstLogin = uFirstLogin;
    }

    public Integer getuCheckSms() {
        return uCheckSms;
    }

    public void setuCheckSms(Integer uCheckSms) {
        this.uCheckSms = uCheckSms;
    }

    public Date getuPassUpdateTime() {
        return uPassUpdateTime;
    }

    public void setuPassUpdateTime(Date uPassUpdateTime) {
        this.uPassUpdateTime = uPassUpdateTime;
    }

    public Integer getuSort() {
        return uSort;
    }

    public void setuSort(Integer uSort) {
        this.uSort = uSort;
    }

    public String getuPushId() {
        return uPushId;
    }

    public void setuPushId(String uPushId) {
        this.uPushId = uPushId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getuSignature() {
        return uSignature;
    }

    public void setuSignature(String uSignature) {
        this.uSignature = uSignature;
    }

    public String getuQualification() {
        return uQualification;
    }

    public void setuQualification(String uQualification) {
        this.uQualification = uQualification;
    }

    public Boolean getuAutoDisable() {
        return uAutoDisable;
    }

    public void setuAutoDisable(Boolean uAutoDisable) {
        this.uAutoDisable = uAutoDisable;
    }

    public String getTeId() {
        return teId;
    }

    public void setTeId(String teId) {
        this.teId = teId;
    }

    public String getuPlaintextNum() {
        return uPlaintextNum;
    }

    public void setuPlaintextNum(String uPlaintextNum) {
        this.uPlaintextNum = uPlaintextNum;
    }

    public String getuClass() {
        return uClass;
    }

    public void setuClass(String uClass) {
        this.uClass = uClass;
    }

    public String getuKey() {
        return uKey;
    }

    public void setuKey(String uKey) {
        this.uKey = uKey;
    }

    public String getuKeyPubKey() {
        return uKeyPubKey;
    }

    public void setuKeyPubKey(String uKeyPubKey) {
        this.uKeyPubKey = uKeyPubKey;
    }

    public String getSign() {
        return sign;
    }

    public String getuPhone() {
        return uPhone;
    }

    public void setuPhone(String uPhone) {
        this.uPhone = uPhone;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    /**
     * 用户加签正文
     *
     * @return
     */
    public String signContent() {
        return this.uAccount + this.uType + this.uMtel + this.uCode + this.uEmail + this.uTel + this.uIdcard + this.uName + this.uPassword;
    }
}
