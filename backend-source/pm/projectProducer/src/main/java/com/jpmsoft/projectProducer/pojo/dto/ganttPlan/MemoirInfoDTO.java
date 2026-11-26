package com.jpmsoft.projectProducer.pojo.dto.ganttPlan;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 大事记
 *
 * @Author: 李文涛 aaron
 * @date: 2024/9/5 11:14
 */
@Data
public class MemoirInfoDTO extends BaseFileRequest implements Serializable {

    private static final long serialVersionUID = -4083775436113302188L;
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

    private String miTitle;
    private String miContent;
    private Date miTime;
    private String miCpy;
    private String taskId;

    private String miCpyName;
    @JsonProperty("pName")
    private String pName;
    private String secName;
    private String conName;
    private String taskText;

    private List<MemoirPraiseDTO> memoirPraises;
    private List<MemoirImageDTO> memoirImages;
    private List<MemoirCommentDTO> memoirComments;

    private String miType;
}
