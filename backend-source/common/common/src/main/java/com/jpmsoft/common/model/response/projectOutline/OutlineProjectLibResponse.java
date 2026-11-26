package com.jpmsoft.common.model.response.projectOutline;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 项目管理大纲体系目录节点
 *
 * @author tianhelin
 * @date 2025/6/13
 */
@Data
public class OutlineProjectLibResponse {
    private String id;
    /**
     * 项目管理大纲体系管理id
     */
    private String opmId;
    private String oplParentId;
    private String oplName;
    private String oplCode;
    private String oplRemark;
    private String oplStatus;
    private Integer oplSort;
    private String oplContent;
    private Boolean oplBussnis;
    private String opnContent;
    private Date createTime;
    private String createUid;
    private String createUname;
    private BigDecimal oplScore;
    /**
     * 项目管理大纲体系-关联业务功能
     */
    private List<OutlineProjectTempResponse> outlineProjectTempList;
    /**
     * 项目管理大纲体系风险指标
     */
    private List<OutlineProjectRiskResponse> outlineProjectRiskList;
    /**
     * 项目管理大纲体系节点数据源配置
     */
    private List<OutlineProjecDatasourceResponse> outlineProjecDatasourceList;
    /**
     * 子节点
     */
    private List<OutlineProjectLibResponse> childNodes;

    private Integer tempCount;
    private Integer riskCount;
    private Integer datasourceCount;

    // 目录显示名字
    private String oplCatalogName;

    // 搜索结果扩展
    private String oplContentExtend;

    // 目录层级（用于导出时处理word的标题）
    private int oplLevel;

    // 匹配索引
    private Integer index;

    private Boolean oplLevelBottom;

    private String tCode;

    private Boolean isEmpty;

    private List<Map<String, String>> preTypeList;
}
