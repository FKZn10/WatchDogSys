package com.jpmsoft.fileUpload.pojo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.fileUpload.pojo.vo.FileResVo;
import lombok.Data;

import java.util.List;

@Data
public class  QsrRecord {
    private Integer qsrState;
    private String qsrRectifyReply;
    private String qsrRectifyUid;
    private String qsrId;
    private String qsrPic;
    private String qsrRectifyPic;
    private String qsrRectifyUame;
    @JsonProperty("pId")
    private String pId;
    private String ptId;
    private String glId;
    @JsonProperty("pName")
    private String pName;
    private String qsrStateTime;
    private String qsrCreateTime;
    private String qsrRectifyRequire;
    private String qsrDes;
    private String qsrRectifyRequireTime;
    private String qsrRectifyFinishTime;
    private List<FileResVo> qsrPicIds;
    private List<FileResVo> qsrRectifyPicIds;
}