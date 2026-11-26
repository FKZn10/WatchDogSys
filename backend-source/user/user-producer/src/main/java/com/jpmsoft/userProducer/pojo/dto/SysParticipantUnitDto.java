package com.jpmsoft.userProducer.pojo.dto;

import com.jpmsoft.common.model.BaseModeRequest;
import com.jpmsoft.userProducer.constants.BaseMsg;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 项目参建单位
 */
public class SysParticipantUnitDto extends BaseModeRequest {

    private String puId;
    @NotBlank(message = BaseMsg.pIdIsNull)
    private String pId;
    @NotBlank(message = BaseMsg.cIdIsNull)
    private String cId;
    private String cType;
    private String cTypeName;
    private Date puCreateTime;
    private String puCreateUid;
    private String puCreateUname;
    private String puRemark;
    private Integer puOrder;

    private String pName;
    private String pNameHistory;
    private String cName;
    private String cNameHistory;
    private String cCreditcode;
    private String cCode;

//    private List<SysCompanyModel> sysCompanys;

//    public class SysCompanyModel {
//        public String cId;
//        public String cType;
//
//        public String getcId() {
//            return cId;
//        }
//
//        public void setcId(String cId) {
//            this.cId = cId;
//        }
//
//        public String getcType() {
//            return cType;
//        }
//
//        public void setcType(String cType) {
//            this.cType = cType;
//        }
//    }

    public String getPuId() {
        return puId;
    }

    public void setPuId(String puId) {
        this.puId = puId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public String getcType() {
        return cType;
    }

    public void setcType(String cType) {
        this.cType = cType;
    }

    public Date getPuCreateTime() {
        return puCreateTime;
    }

    public void setPuCreateTime(Date puCreateTime) {
        this.puCreateTime = puCreateTime;
    }

    public String getPuCreateUid() {
        return puCreateUid;
    }

    public void setPuCreateUid(String puCreateUid) {
        this.puCreateUid = puCreateUid;
    }

    public String getPuCreateUname() {
        return puCreateUname;
    }

    public void setPuCreateUname(String puCreateUname) {
        this.puCreateUname = puCreateUname;
    }

    public String getPuRemark() {
        return puRemark;
    }

    public void setPuRemark(String puRemark) {
        this.puRemark = puRemark;
    }

    public Integer getPuOrder() {
        return puOrder;
    }

    public void setPuOrder(Integer puOrder) {
        this.puOrder = puOrder;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpNameHistory() {
        return pNameHistory;
    }

    public void setpNameHistory(String pNameHistory) {
        this.pNameHistory = pNameHistory;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcNameHistory() {
        return cNameHistory;
    }

    public void setcNameHistory(String cNameHistory) {
        this.cNameHistory = cNameHistory;
    }

    public String getcCreditcode() {
        return cCreditcode;
    }

    public void setcCreditcode(String cCreditcode) {
        this.cCreditcode = cCreditcode;
    }

    //    public List<SysCompanyModel> getSysCompanys() {
//        return sysCompanys;
//    }
//
//    public void setSysCompanys(List<SysCompanyModel> sysCompanys) {
//        this.sysCompanys = sysCompanys;
//    }


    public String getcCode() {
        return cCode;
    }

    public void setcCode(String cCode) {
        this.cCode = cCode;
    }

    public String getcTypeName() {
        return cTypeName;
    }

    public void setcTypeName(String cTypeName) {
        this.cTypeName = cTypeName;
    }
}
