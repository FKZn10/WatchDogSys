package com.jpmsoft.projectProducer.pojo.dto.visaChange;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/4/6 17:51
 */
@Data
public class ConDccsDTO extends BaseFileRequest implements Serializable {

    @ProcessFields(type = ProcessFieldEnum.id)
    private String dccsId;
    @JsonProperty("pId")
    private String pId;
    private String conId;
    private String dccsCode;
    private String dccsName;
    private String dccsMajor;
    private String dccsMainUnit;
    @ProcessFields(type = ProcessFieldEnum.createUid)
    private String createUid;
    @ProcessFields(type = ProcessFieldEnum.createUname)
    private String createUname;
    @ProcessFields(type = ProcessFieldEnum.createTime)
    private Date createTime;
    @ProcessFields(type = ProcessFieldEnum.state)
    private Integer state;
    @ProcessFields(type = ProcessFieldEnum.stateTime)
    private Date stateTime;

    @JsonProperty("pName")
    private String pName;
    private String conName;
    private String dccsMainUnitName;

    private List<ConDccsCompanyDTO> conDccsCompanys;
}
