package com.jpmsoft.projectProducer.pojo.dto.design;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 施工图审查
 *
 * @Author: 李文涛 aaron
 * @date: 2023/10/13 10:58
 */
@Data
public class DesignDrawReviewDTO extends BaseFileRequest implements Serializable {

    private static final long serialVersionUID = 5418036290863375954L;
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

    private String drTitle;
    private String drHandlers;
    private Date drInitTime;
    private String drRemark;

    private String drHandlersName;
    @JsonProperty("pName")
    private String pName;
    @JsonProperty("pType")
    private String pType;
    private List<DesignDrawReviewDtlDTO> designDrawReviewDtls;
}
