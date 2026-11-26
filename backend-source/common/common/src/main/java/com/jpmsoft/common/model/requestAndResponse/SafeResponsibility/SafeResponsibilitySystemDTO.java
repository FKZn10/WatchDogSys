package com.jpmsoft.common.model.requestAndResponse.SafeResponsibility;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 安全责任体系数据传输对象。
 */
@Data
public class SafeResponsibilitySystemDTO {
    private String id;
    private String systemName;
    private Integer systemType;
    private String pId;
    private String conId;
    private String sourceSystemId;
    private Integer versionNo;
    private Boolean status;
    private String remark;
    private String createUid;
    private String createUname;
    private Date createTime;
    private String updateUid;
    private String updateUname;
    private Date updateTime;
    /**
     * 树结构关键字，可匹配层级名称、岗位名称和职责内容
     */
    private String keyword;
    private List<SafeResponsibilityLevelDTO> levelList;
}
