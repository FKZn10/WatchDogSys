package com.jpmsoft.fileUpload.pojo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户使用手册
 *
 * @Author: 李文涛 aaron
 * @date: 2023/12/26 9:05
 */
@Data
public class SysUserManualDTO implements Serializable {

    private static final long serialVersionUID = 4573101976450429467L;

    private String id;
    private String umTitle;
    private String umContent;
    private String umCode;
    private String umKeys;
    private String umParentId;
    private Integer umState;
    private Integer umSort;
    private String createUname;
    private String createUid;
    private Date createTime;
    @JsonProperty("tCode")
    private String tCode;
    private String powerCode;

    private String rteFileRelationId;
    private String rteFileIds;
    private List<SysUserManualDTO> childNodes;
}
