package com.jpmsoft.projectProducer.pojo.vo.oa;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/7/25 13:47
 */
@Data
public class OaKnowledgeGroupVO implements Serializable {


    private static final long serialVersionUID = -405871462891974039L;
    private String id;
    private String kbId;
    private String kbgName;
    private String kbgType;
    private String kbgGroupId;
    private String kbgSecrecyLevel;
    private String kbgRole;
    private String kbgRoleType;
    private String kbgPower;
    private Date createTime;

    private String kbgGroupName;
    private String roleCode;
    private String roleName;
}
