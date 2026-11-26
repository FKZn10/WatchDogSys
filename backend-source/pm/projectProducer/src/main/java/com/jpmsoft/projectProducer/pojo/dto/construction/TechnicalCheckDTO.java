package com.jpmsoft.projectProducer.pojo.dto.construction;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.util.Date;

/**
 * 技术核定单
 *
 * @Author: 李文涛 aaron
 * @date: 2024/3/29 16:19
 */
@Data
public class TechnicalCheckDTO extends BaseFileRequest {

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
    private String tcBuildId;
    private String tcName;
    private String tcCode;
    private String tcConstructionId;
    private String tcDesignId;
    private String tcContent;
    private String tcRemark;
    private String tcImgName;
    private String tcTitle;
    private String tcConstructionName;
    private String tcDesignName;
    private String tcBuildName;
    @JsonProperty("pName")
    private String pName;
    @JsonProperty("pCode")
    private String pCode;
}
