package com.jpmsoft.common.model.requestAndResponse.Construction;

import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.ProjectCommonRes;

import java.util.Date;

/**
 * 工程延期报审
 */
public class ConDelayModel extends ProjectCommonRes {

    @ProcessFields(type = ProcessFieldEnum.id)
    private String deId;
    private String deTitle;
    private String deReason;
    private Integer deDays;
    private String deRemark;
    @ProcessFields(type = ProcessFieldEnum.createUid)
    private String deCreateUid;
    @ProcessFields(type = ProcessFieldEnum.createUname)
    private String deCreateUname;
    @ProcessFields(type = ProcessFieldEnum.createTime)
    private Date deCreateTime;
    @ProcessFields(type = ProcessFieldEnum.state)
    private Integer deState;
    @ProcessFields(type = ProcessFieldEnum.stateTime)
    private Date deStateTime;
    private String pId;


    public String getDeId() {
        return deId;
    }

    public void setDeId(String deId) {
        this.deId = deId;
    }

    public String getDeTitle() {
        return deTitle;
    }

    public void setDeTitle(String deTitle) {
        this.deTitle = deTitle;
    }

    public String getDeReason() {
        return deReason;
    }

    public void setDeReason(String deReason) {
        this.deReason = deReason;
    }

    public Integer getDeDays() {
        return deDays;
    }

    public void setDeDays(Integer deDays) {
        this.deDays = deDays;
    }

    public String getDeRemark() {
        return deRemark;
    }

    public void setDeRemark(String deRemark) {
        this.deRemark = deRemark;
    }

    public String getDeCreateUid() {
        return deCreateUid;
    }

    public void setDeCreateUid(String deCreateUid) {
        this.deCreateUid = deCreateUid;
    }

    public String getDeCreateUname() {
        return deCreateUname;
    }

    public void setDeCreateUname(String deCreateUname) {
        this.deCreateUname = deCreateUname;
    }

    public Date getDeCreateTime() {
        return deCreateTime;
    }

    public void setDeCreateTime(Date deCreateTime) {
        this.deCreateTime = deCreateTime;
    }

    public Integer getDeState() {
        return deState;
    }

    public void setDeState(Integer deState) {
        this.deState = deState;
    }

    public Date getDeStateTime() {
        return deStateTime;
    }

    public void setDeStateTime(Date deStateTime) {
        this.deStateTime = deStateTime;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }
}
