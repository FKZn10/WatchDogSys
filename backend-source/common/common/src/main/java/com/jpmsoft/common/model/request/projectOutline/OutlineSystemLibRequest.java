package com.jpmsoft.common.model.request.projectOutline;

import com.jpmsoft.common.model.BaseFileRequest;
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
public class OutlineSystemLibRequest extends BaseFileRequest {
    private String id;
    private List<String> ids;
    private String oslParentId;
    private String oslName;
    private List<String> oslNameList;
    private String oslCode;
    private List<String> oslCodeList;
    private String oslRemark;
    private String oslStatus;
    private List<String> oslStatusList;
    private Integer oslSort;
    /**
     * 体系模板管理id
     */
    private String osmId;
    private String oslContent;
    private Boolean oslBussnis;
    private Date createTime;
    private String createUid;
    private String createUname;
    /**
     * 体系模板管理
     */
    private List<OutlineNodeTempRequest> outlineNodeTempList;
    /**
     * 体系库风险指标
     */
    private List<OutlineLibRiskRequest> outlineLibRiskList;
    /**
     * 体系库节点数据源
     */
    private List<OutlineNodeDatasourceRequest> outlineNodeDatasourceList;

    // 富文本编辑器文件
    private String oslFileIds;
    private String oslFileRelationId;

    // 排序规则
    private String sortRules;

    // 交换顺序使用
    private String newId;

    // 替换使用
    private String newOslName;

    // 是否去除内容
    private Boolean noContent;
}