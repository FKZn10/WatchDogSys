package com.jpmsoft.common.model.requestAndResponse.Oa;

import com.jpmsoft.common.model.BaseModeRequest;

/**
 * 考勤人员
 */
public class OaAttendanceMemberModel extends BaseModeRequest {

    private String amId;
    private String agId;
    private String amUid;
    private String amUname;
    private String ppId;

    private String agName;
    private String ppName;
    private String pdName;
    private String uMtel;
    private String uTel;
    private String Cname;
    private String cAbbName;

    public String getAmId() {
        return amId;
    }

    public void setAmId(String amId) {
        this.amId = amId;
    }

    public String getAgId() {
        return agId;
    }

    public void setAgId(String agId) {
        this.agId = agId;
    }

    public String getAmUid() {
        return amUid;
    }

    public void setAmUid(String amUid) {
        this.amUid = amUid;
    }

    public String getAmUname() {
        return amUname;
    }

    public void setAmUname(String amUname) {
        this.amUname = amUname;
    }

    public String getPpId() {
        return ppId;
    }

    public void setPpId(String ppId) {
        this.ppId = ppId;
    }

    public String getAgName() {
        return agName;
    }

    public void setAgName(String agName) {
        this.agName = agName;
    }

    public String getPpName() {
        return ppName;
    }

    public void setPpName(String ppName) {
        this.ppName = ppName;
    }

    public String getPdName() {
        return pdName;
    }

    public void setPdName(String pdName) {
        this.pdName = pdName;
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

    public String getCname() {
        return Cname;
    }

    public void setCname(String cname) {
        Cname = cname;
    }

    public String getcAbbName() {
        return cAbbName;
    }

    public void setcAbbName(String cAbbName) {
        this.cAbbName = cAbbName;
    }
}
