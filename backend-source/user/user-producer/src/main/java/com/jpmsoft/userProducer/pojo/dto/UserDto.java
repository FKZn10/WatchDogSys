package com.jpmsoft.userProducer.pojo.dto;

import com.jpmsoft.common.model.BaseModeRequest;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class UserDto extends BaseModeRequest implements Serializable {

    private static final long serialVersionUID = -6951683133047398202L;
    public String uId;
    public String uAccount;
    public String uPassword;
    public String uName;
    public String uState;
    public String plId;
    public String cId;
    public String uCode;
    public String uMtel;
    public String uTel;
    public String uEmail;
    public String uSex;
    public Date uBirthday;
    public String uMdmNo;
    public String uType;
    public String uCreateuName;
    public String uCreateuId;
    public String cName;
    private String start;
    private String end;
    private String uOffice;
    private String uCornet;
    private String uIdcard;
    private Integer uSort;
    private String uPushId;
    private String uSignature;
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
    private String authCode;
    private String dId;
    private String pId;
    private String pName;
    private String uSignatureIds;
    /**
     * 是否过滤掉没有岗位的用户
     */
    private boolean filterPosition;
    private String depCid;
    /**
     * 是否发送短信
     */
    private boolean sendSms = false;
    /**
     * 短信验证码
     */
    private String smsCode;
    private String uQualification;
    private String uQualificationIds;
    private String pdId;
    private String ppId;
    private String ppName;
    private String ppCode;
    private Boolean all;
    private String teId;
    private String appVersion;
    private String appDeviceType;
    private String projectName;
    private String pdName;
    private String uPhone;
    private List<String> excludeClass;
    private List<String> pIds;
    private List<String> positionNames;

    private List<String> uIdList;

    private String pdType;

    private String pdCode;

    private String projectId;

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

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getPdCode() {
        return pdCode;
    }

    public void setPdCode(String pdCode) {
        this.pdCode = pdCode;
    }

    public String getPdType() {
        return pdType;
    }

    public void setPdType(String pdType) {
        this.pdType = pdType;
    }

    public List<String> getuIdList() {
        return uIdList;
    }

    public void setuIdList(List<String> uIdList) {
        this.uIdList = uIdList;
    }

    public List<String> getPositionNames() {
        return positionNames;
    }

    public void setPositionNames(List<String> positionNames) {
        this.positionNames = positionNames;
    }

    public List<String> getpIds() {
        return pIds;
    }

    public void setpIds(List<String> pIds) {
        this.pIds = pIds;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
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

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuState() {
        return uState;
    }

    public void setuState(String uState) {
        this.uState = uState;
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

    public String getuMdmNo() {
        return uMdmNo;
    }

    public void setuMdmNo(String uMdmNo) {
        this.uMdmNo = uMdmNo;
    }

    public String getuType() {
        return uType;
    }

    public void setuType(String uType) {
        this.uType = uType;
    }

    public String getuCreateuName() {
        return uCreateuName;
    }

    public void setuCreateuName(String uCreateuName) {
        this.uCreateuName = uCreateuName;
    }

    public String getuCreateuId() {
        return uCreateuId;
    }

    public void setuCreateuId(String uCreateuId) {
        this.uCreateuId = uCreateuId;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getuCornet() {
        return uCornet;
    }

    public void setuCornet(String uCornet) {
        this.uCornet = uCornet;
    }

    public String getuOffice() {
        return uOffice;
    }

    public void setuOffice(String uOffice) {
        this.uOffice = uOffice;
    }

    public String getuIdcard() {
        return uIdcard;
    }

    public void setuIdcard(String uIdcard) {
        this.uIdcard = uIdcard;
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

    public String getuSignature() {
        return uSignature;
    }

    public void setuSignature(String uSignature) {
        this.uSignature = uSignature;
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

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getdId() {
        return dId;
    }

    public void setdId(String dId) {
        this.dId = dId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getuSignatureIds() {
        return uSignatureIds;
    }

    public void setuSignatureIds(String uSignatureIds) {
        this.uSignatureIds = uSignatureIds;
    }

    public boolean isFilterPosition() {
        return filterPosition;
    }

    public void setFilterPosition(boolean filterPosition) {
        this.filterPosition = filterPosition;
    }

    public String getDepCid() {
        return depCid;
    }

    public void setDepCid(String depCid) {
        this.depCid = depCid;
    }

    public boolean isSendSms() {
        return sendSms;
    }

    public void setSendSms(boolean sendSms) {
        this.sendSms = sendSms;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getuQualification() {
        return uQualification;
    }

    public void setuQualification(String uQualification) {
        this.uQualification = uQualification;
    }

    public String getuQualificationIds() {
        return uQualificationIds;
    }

    public void setuQualificationIds(String uQualificationIds) {
        this.uQualificationIds = uQualificationIds;
    }

    public String getPdId() {
        return pdId;
    }

    public void setPdId(String pdId) {
        this.pdId = pdId;
    }

    public String getPpId() {
        return ppId;
    }

    public void setPpId(String ppId) {
        this.ppId = ppId;
    }

    public String getPpName() {
        return ppName;
    }

    public void setPpName(String ppName) {
        this.ppName = ppName;
    }

    public String getPpCode() {
        return ppCode;
    }

    public void setPpCode(String ppCode) {
        this.ppCode = ppCode;
    }

    public Boolean getAll() {
        return all;
    }

    public void setAll(Boolean all) {
        this.all = all;
    }

    public String getTeId() {
        return teId;
    }

    public void setTeId(String teId) {
        this.teId = teId;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAppDeviceType() {
        return appDeviceType;
    }

    public void setAppDeviceType(String appDeviceType) {
        this.appDeviceType = appDeviceType;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getPdName() {
        return pdName;
    }

    public void setPdName(String pdName) {
        this.pdName = pdName;
    }

    public String getuPhone() {
        return uPhone;
    }

    public void setuPhone(String uPhone) {
        this.uPhone = uPhone;
    }

    public List<String> getExcludeClass() {
        return excludeClass;
    }

    public void setExcludeClass(List<String> excludeClass) {
        this.excludeClass = excludeClass;
    }
}
