package com.jpmsoft.projectProducer.pojo.vo.construction;

import com.jpmsoft.common.model.ProjectCommonRes;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 施工质量安全日志
 *
 * @author: 李文涛 aaron
 * @date: 2022/07/08 10:07
 * @TableName Build_Quality_Safety_Log
 */
@Data
public class BuildQualitySafetyLogVo extends ProjectCommonRes implements Serializable {


    private static final long serialVersionUID = 8756516932390986883L;

    private String id;
    private String secId;
    private Date bqslDate;
    private String createUid;
    private String createUname;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    private String bqslRemarks;
    private String secName;
    private String secCode;
}