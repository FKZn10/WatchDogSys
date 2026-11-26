package com.jpmsoft.projectProducer.pojo.dto.oa;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/7/25 13:47
 */
@Data
public class OaKnowledgeGroupDTO implements Serializable {

    private static final long serialVersionUID = -7786522069207915279L;
    private String id;
    private String kbId;
    private String kbgName;
    private String kbgType;
    private String kbgGroupId;
    private String kbgSecrecyLevel;
    private String kbgRoleType;
    private String kbgRole;
    private String kbgPower;
    private Date createTime;

}
