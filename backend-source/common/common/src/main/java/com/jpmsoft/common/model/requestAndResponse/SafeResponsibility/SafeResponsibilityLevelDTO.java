package com.jpmsoft.common.model.requestAndResponse.SafeResponsibility;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 安全责任层级数据传输对象。
 */
@Data
public class SafeResponsibilityLevelDTO {
    private String id;
    private String systemId;
    private String parentId;
    private String levelName;
    private String levelDescription;
    private String levelPath;
    private Integer levelDepth;
    private Integer sortNo;
    private Boolean status;
    private String createUid;
    private String createUname;
    private Date createTime;
    private String updateUid;
    private String updateUname;
    private Date updateTime;
    /**
     * 树结构筛选关键字，可匹配层级、岗位和职责内容。
     */
    private String keyword;
    private List<SafeResponsibilityLevelDTO> childNodes;
    private List<SafeResponsibilityPostDTO> postList;
}
