
package com.jpmsoft.projectProducer.pojo.dto.construction;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 夜间施工备案
 *
 * @Author: 李文涛 aaron
 * @date: 2023/10/24 10:48
 */
@Data
public class NightConstructionFilingDTO extends BaseFileRequest implements Serializable {

    private static final long serialVersionUID = 1736682498306632033L;
    @ProcessFields(type = ProcessFieldEnum.id)
    private String id;
    @ProcessFields(type = ProcessFieldEnum.state)
    private Integer state;
    @ProcessFields(type = ProcessFieldEnum.stateTime)
    private Date stateTime;
    @ProcessFields(type = ProcessFieldEnum.createUid)
    private String createUid;
    @ProcessFields(type = ProcessFieldEnum.createUname)
    private String createUname;
    @ProcessFields(type = ProcessFieldEnum.createTime)
    private Date createTime;
    private String conId;
    private String ncfLeader;
    private String ncfLeaderPhone;
    private String ncfWorkContent;
    private Integer ncfWorkNumber;
    private String ncfAreaSection;
    private Date ncfStartTime;
    private Date ncfEndTime;
    private String ncfWorker;
    private String ncfWorkerPhone;
    private String ncfSide;
    private String ncfSidePhone;
    private String ncfRemark;

    @JsonProperty("pName")
    private String pName;
    @JsonProperty("pCode")
    private String pCode;
    private String conName;
    private String conCode;
}
