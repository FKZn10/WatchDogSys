package com.jpmsoft.common.model.response.projectOutline;

import com.jpmsoft.common.model.BaseFileResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * 体系模板管理
 *
 * @author tianhelin
 * @date 2025/6/11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OutlineSystemManageResponse extends BaseFileResponse {
    private String id;
    private String osmName;
    private String osmCode;
    private String osmStatus;
    private String osmRemark;
    private String osmProjectType;
    private String osmDepartmentId;
    private Date createTime;
    private String createUid;
    private String createUname;
    private Integer state;
    private Date stateTime;
    private Date updateTime;
    private String updateUid;
    private String updateUname;
    private String sortRules;
    /**
     * 体系模板-目录节点
     */
    private List<OutlineSystemLibResponse> outlineSystemLibList;

    /**
     * 体系模板-关联业务功能
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
}
