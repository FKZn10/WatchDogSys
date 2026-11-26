package com.jpmsoft.common.model.request.Archive;

import com.jpmsoft.common.model.BaseModeRequest;
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
public class ArchiveDirectoryGroupDto extends BaseModeRequest implements Serializable {

    private static final long serialVersionUID = -4810963866756155627L;
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
}
