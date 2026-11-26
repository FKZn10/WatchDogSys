package com.jpmsoft.common.model.response.projectOutline;

import com.jpmsoft.common.model.BaseFileResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * 体系模板-目录节点
 *
 * @author tianhelin
 * @date 2025/6/11
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OutlineSystemLibResponse extends BaseFileResponse {
    private String id;
    /**
     * 体系模板管理id
     */
    private String osmId;
    private String oslParentId;
    private String oslName;
    private String oslCode;
    private String oslRemark;
    private String oslStatus;
    private Integer oslSort;
    private String oslContent;
    private Boolean oslBussnis;
    private Date createTime;
    private String createUid;
    private String createUname;
    /**
     * 体系模板管理
     */
    private List<OutlineNodeTempResponse> outlineNodeTempList;
    /**
     * 体系库风险指标
     */
    private List<OutlineLibRiskResponse> outlineLibRiskList;
    /**
     * 体系库节点数据源
     */
    private List<OutlineNodeDatasourceResponse> outlineNodeDatasourceList;
    /**
     * 子节点
     */
    private List<OutlineSystemLibResponse> childNodes;

    // 富文本编辑器文件
    private String oslFileIds;
    private String oslFileRelationId;

    // 目录显示名字
    private String oslCatalogName;

    // 搜索结果扩展
    private String oslContentExtend;

    // 匹配索引
    private Integer index;

    private Boolean oslLevelBottom;

    private Boolean isEmpty;
}