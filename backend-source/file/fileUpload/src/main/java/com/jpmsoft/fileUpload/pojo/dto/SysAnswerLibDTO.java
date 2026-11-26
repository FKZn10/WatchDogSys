package com.jpmsoft.fileUpload.pojo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 问答知识库
 *
 * @Author: 李文涛 aaron
 * @date: 2023/12/25 9:54
 */
@Data
public class SysAnswerLibDTO extends BaseModeRequest implements Serializable {

    private static final long serialVersionUID = 802970806645210195L;
    private String id;
    private String alTitle;
    private String alContent;
    private String alType;
    private String alWebUrl;
    private String alAppUrl;
    private Boolean alAsked;
    private String  alCode;
    private String  alKeys;
    private String createUname;
    private String createUid;
    private Date createTime;
    /**
     * 问题
     */
    private String problem;

    private String rteFileRelationId;
    private String rteFileIds;

    @JsonProperty("tCode")
    private String tCode;
    private String powerCode;
}
