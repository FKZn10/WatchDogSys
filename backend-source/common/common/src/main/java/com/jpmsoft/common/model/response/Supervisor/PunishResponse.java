package com.jpmsoft.common.model.response.Supervisor;

import com.jpmsoft.common.model.ProjectCommonRes;
import lombok.Data;

import java.util.Date;

/**
 * 违规处罚
 */
@Data
public class PunishResponse extends ProjectCommonRes {
    private String id;
    private String createUid;
    private String createUname;
    private Date createTime;
    private Integer State;
    private Date stateTime;
    private String pId;
    private String conId;
    private String cId;
    private String illegalBehavior;
    private String punitiveMeasures;
    private String punishType;
    private String remark;
    private Date punishStartTime;
    private Date punishEndTime;
    private String pName;
    private String conName;
    private String cName;
}
