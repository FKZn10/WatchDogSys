package com.jpmsoft.projectConsumer.pojo.dto.secInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 专家考核申诉
 *
 * @Author: 李文涛 aaron
 * @date: 2023/8/1 9:52
 */
@Data
public class SecExpertAppealDTO extends BaseFileRequest {

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

    private String exId;
    @JsonProperty("pId")
    private String pId;
    private String secId;
    private Date seaTime;

    private String exName;
    private String secName;
    @JsonProperty("pName")
    private String pName;
    @JsonProperty("pCode")
    private String pCode;
    private List<SecExpertAppealDtlDTO> secExpertAppealDtls;
}
