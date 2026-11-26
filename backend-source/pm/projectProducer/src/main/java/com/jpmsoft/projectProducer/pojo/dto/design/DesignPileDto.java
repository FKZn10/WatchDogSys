package com.jpmsoft.projectProducer.pojo.dto.design;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 桩基检测
 * Created By:李文涛 aaron
 * Created Time:2022/6/22 13:55
 */
@Data
public class DesignPileDto extends BaseFileRequest implements Serializable {

    private static final long serialVersionUID = -532164700350561470L;
    @ProcessFields(type = ProcessFieldEnum.id)
    private String dpId;
    @JsonProperty("pId")
    private String pId;
    private String conId;
    private String secId;
    private String dpSecName;
    private String dpType;
    private String dpPm;
    private Date dpStartTime;
    private Date dpEndTime;
    private String dpContent;
    @ProcessFields(type = ProcessFieldEnum.state)
    private Integer state;
    @ProcessFields(type = ProcessFieldEnum.stateTime)
    private Date stateTime;
    @ProcessFields(type = ProcessFieldEnum.createUid)
    private String dpCreateUid;
    @ProcessFields(type = ProcessFieldEnum.createUname)
    private String dpCreateUname;
    @ProcessFields(type = ProcessFieldEnum.createTime)
    private Date dpCreateTime;

    @JsonProperty("pName")
    private String pName;
    @JsonProperty("pCode")
    private String pCode;
    private String conName;
    private String conCode;
}
