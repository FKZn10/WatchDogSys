package com.jpmsoft.common.model.requestAndResponse.Oa;

import com.jpmsoft.common.model.BaseModeRequest;

import java.util.Date;

/**
 * 考勤记录
 */
public class OaAttendanceModel extends BaseModeRequest {

    private String aId;
    private String agId;
    private String aType;
    private String aUid;
    private String aUname;
    private String cId;
    private Date aTime;
    private String aPic;
    private Date aCreateTime;
    private String aLongitude;
    private String aLatitude;
    private String aWeek;
    private String aStartAm;
    private String aEndAm;
    private String aStartPm;
    private String aEndPm;
    private Float aHours;
    private String aResult;
    private String aWay;
    private Integer aIsFieid;
    private String aRemark;

    private String agName;
    private String cName;
    private String aPicIds;
    private String cAbbName;

    public String getaId() {
        return aId;
    }

    public void setaId(String aId) {
        this.aId = aId;
    }

    public String getAgId() {
        return agId;
    }

    public void setAgId(String agId) {
        this.agId = agId;
    }

    public String getaType() {
        return aType;
    }

    public void setaType(String aType) {
        this.aType = aType;
    }

    public String getaUid() {
        return aUid;
    }

    public void setaUid(String aUid) {
        this.aUid = aUid;
    }

    public String getaUname() {
        return aUname;
    }

    public void setaUname(String aUname) {
        this.aUname = aUname;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public Date getaTime() {
        return aTime;
    }

    public void setaTime(Date aTime) {
        this.aTime = aTime;
    }

    public String getaPic() {
        return aPic;
    }

    public void setaPic(String aPic) {
        this.aPic = aPic;
    }

    public Date getaCreateTime() {
        return aCreateTime;
    }

    public void setaCreateTime(Date aCreateTime) {
        this.aCreateTime = aCreateTime;
    }

    public String getaLongitude() {
        return aLongitude;
    }

    public void setaLongitude(String aLongitude) {
        this.aLongitude = aLongitude;
    }

    public String getaLatitude() {
        return aLatitude;
    }

    public void setaLatitude(String aLatitude) {
        this.aLatitude = aLatitude;
    }

    public String getaWeek() {
        return aWeek;
    }

    public void setaWeek(String aWeek) {
        this.aWeek = aWeek;
    }

    public String getaStartAm() {
        return aStartAm;
    }

    public void setaStartAm(String aStartAm) {
        this.aStartAm = aStartAm;
    }

    public String getaEndAm() {
        return aEndAm;
    }

    public void setaEndAm(String aEndAm) {
        this.aEndAm = aEndAm;
    }

    public String getaStartPm() {
        return aStartPm;
    }

    public void setaStartPm(String aStartPm) {
        this.aStartPm = aStartPm;
    }

    public String getaEndPm() {
        return aEndPm;
    }

    public void setaEndPm(String aEndPm) {
        this.aEndPm = aEndPm;
    }

    public Float getaHours() {
        return aHours;
    }

    public void setaHours(Float aHours) {
        this.aHours = aHours;
    }

    public String getaResult() {
        return aResult;
    }

    public void setaResult(String aResult) {
        this.aResult = aResult;
    }

    public String getaWay() {
        return aWay;
    }

    public void setaWay(String aWay) {
        this.aWay = aWay;
    }

    public String getaRemark() {
        return aRemark;
    }

    public void setaRemark(String aRemark) {
        this.aRemark = aRemark;
    }

    public String getAgName() {
        return agName;
    }

    public void setAgName(String agName) {
        this.agName = agName;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getaPicIds() {
        return aPicIds;
    }

    public void setaPicIds(String aPicIds) {
        this.aPicIds = aPicIds;
    }

    public Integer getaIsFieid() {
        return aIsFieid;
    }

    public void setaIsFieid(Integer aIsFieid) {
        this.aIsFieid = aIsFieid;
    }

    public String getcAbbName() {
        return cAbbName;
    }

    public void setcAbbName(String cAbbName) {
        this.cAbbName = cAbbName;
    }
}

