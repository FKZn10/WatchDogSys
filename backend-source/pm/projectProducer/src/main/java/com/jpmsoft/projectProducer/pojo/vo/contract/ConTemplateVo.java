package com.jpmsoft.projectProducer.pojo.vo.contract;

import com.jpmsoft.common.model.ProjectCommonRes;
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
public class ConTemplateVo extends ProjectCommonRes implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private String contInitiate;
    private String contName;
    private String contVersion;
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

    private String conName;
    private String conCode;

    private String contMarKey;
    private String contNew;
}