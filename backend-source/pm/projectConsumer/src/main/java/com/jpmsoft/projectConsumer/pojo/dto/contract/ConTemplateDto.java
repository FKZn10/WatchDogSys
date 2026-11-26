package com.jpmsoft.projectConsumer.pojo.dto.contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 合同范本.
 *
 * @author: 李文涛 aaron
 * @date: 2022/07/08 10:07
 * @TableName Con_Template
 */
@Data
public class ConTemplateDto extends BaseFileRequest implements Serializable {

    private String id;

    private String contInitiate;

    private String contName;

    private String contVersion;
    @JsonProperty("pId")
    private String pId;

    private String conId;

    private String conType;

    private String contDepartmentId;

    private String contFile;

    private String createUid;

    private String createUname;

    private Date createTime;

    private Date stateTime;

    private Integer state;

    private Integer contEnable;

    private String contCode;

    private String contDescription;

    private String contFileIds;
    @JsonProperty("pName")
    private String pName;
    private String conName;
    private static final long serialVersionUID = 1L;

    private String contMarKey;
    private String contNew;
}