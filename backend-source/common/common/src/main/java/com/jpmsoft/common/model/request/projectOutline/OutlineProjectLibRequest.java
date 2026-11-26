package com.jpmsoft.common.model.request.projectOutline;

import com.jpmsoft.common.model.BaseFileResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 项目管理大纲体系目录节点
 *
 * @author tianhelin
 * @date 2025/6/13
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OutlineProjectLibRequest extends BaseFileResponse {
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
    List<OutlineProjectTempRequest> outlineProjectTempList;
    /**
     * 项目管理大纲体系风险指标
     */
    List<OutlineProjectRiskRequest> outlineProjectRiskList;
    /**
     * 项目管理大纲体系节点数据源配置
     */
    List<OutlineProjecDatasourceRequest> outlineProjecDatasourceList;

    // 富文本编辑器文件
    private String oplFileIds;
    private String oplFileRelationId;

    // 排序规则
    private String sortRules;

    // 交换顺序使用
    private String newId;

    // 替换使用
    private String newOplName;

    // 用于精确查询
    private String oplNameExact;

    // 是否去除内容
    private Boolean noContent;

    private String opmCode;
}
