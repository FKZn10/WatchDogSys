package com.jpmsoft.projectProducer.pojo.vo.archive;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 档案目录权限 -组和用户
 *
 * @author: 李文涛 aaron
 * @date: 2022/11/9 17:13
 */
@Data
public class ArchiveDirectoryGroupVo implements Serializable {

    private static final long serialVersionUID = 889959317903712361L;
    private String id;
    private String adId;
    private String adgName;
    private String adgType;
    private String adgGroupId;
    private String adgSecrecyLevel;
    private String adgRoleType;
    private String adgRole;
    private String adgPower;
    private Date createTime;
    private String adgGroupName;

    private String roleName;
    private String roleCode;
}
