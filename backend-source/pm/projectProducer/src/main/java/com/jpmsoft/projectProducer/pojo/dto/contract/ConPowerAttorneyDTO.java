package com.jpmsoft.projectProducer.pojo.dto.contract;

import com.jpmsoft.common.model.ProjectCommonRes;
import lombok.Data;

import java.util.Date;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/3/7 9:46
 */
@Data
public class ConPowerAttorneyDTO extends ProjectCommonRes {

    private String id;
    private Integer state;
    private Date stateTime;
    private String createUid;
    private String createUname;
    private Date createTime;

    private String conCode;
    private String conName;
    private String paConCode;
    private String paAuthPerson;
    private String paRemark;
    private String conCategory;

}
