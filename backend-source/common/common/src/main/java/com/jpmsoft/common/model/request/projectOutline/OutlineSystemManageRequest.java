package com.jpmsoft.common.model.request.projectOutline;

import com.jpmsoft.common.model.BaseFileRequest;
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
public class OutlineSystemManageRequest extends BaseFileRequest {
    private String id;
    private List<String> ids;
    private String osmName;
    private List<String> osmNameList;
    private String osmCode;
    private List<String> osmCodeList;
    private String osmStatus;
    private List<String> osmStatusList;
    private String osmRemark;
    private String osmProjectType;
    private List<String> osmProjectTypeList;
    private String osmDepartmentId;
    private List<String> osmDepartmentIdList;
    private Date createTime;
    private String createUid;
    private String createUname;
    private Integer state;
    private List<Integer> stateList;
    private Date stateTime;
    private Date updateTime;
    private String updateUid;
    private String updateUname;
    private String sortRules;
    /**
     * 体系模板-目录节点
     */
    private List<OutlineSystemLibRequest> outlineSystemLibList;

    /**
     * 体系模板-关联业务功能
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
}
