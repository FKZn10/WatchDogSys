package com.jpmsoft.projectProducer.pojo.dto.design;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.base.FileFields;
import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.util.Date;

/**
 * 设计文件审核
 *
 * @Author: 李文涛 aaron
 * @date: 2023/9/19 9:27
 */
@Data
public class DesignDocReviewDTO extends BaseFileRequest {

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
    private String drName;
    private String drUnitId;
    @JsonProperty("pId")
    private String pId;
    private String drType;
    private String drStage;
    private Date drUploadTime;
    @FileFields(index = 1)
    private String drFile;
    private String drDescribe;
    private String conId;


    private String drUnitName;
    @FileFields(index = 1,type = "drFileIds")
    private String drFileIds;
    @JsonProperty("pName")
    private String pName;
    @JsonProperty("pCode")
    private String pCode;
    private String conName;
    private String conCode;
}
