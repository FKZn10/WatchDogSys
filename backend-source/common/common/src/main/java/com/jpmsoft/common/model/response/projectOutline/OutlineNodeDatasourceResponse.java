package com.jpmsoft.common.model.response.projectOutline;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 体系模板节点数据源
 *
 * @author tianhelin
 * @date 2025/6/11
 */
@Data
public class OutlineNodeDatasourceResponse {
    private String id;
    /**
     * 体系模板-目录节点ID
     */
    private String oslId;
    /**
     * 数据源配置ID
     */
    private String odId;
    private Date createTime;
    private String createUid;
    private String createUname;

    // 下面是数据源的字段
    private String odName;
    private String odType;
    private String odSource;
    private String odStatus;
    private String odRemark;
    private String odAddress;
    private String odMethod;
    private String odBody;
    private String odDataType;
    private String odAlias;

    private List<String> tCodes;
}