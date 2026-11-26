package com.jpmsoft.common.model.request.projectOutline;

import com.jpmsoft.common.model.BasePageEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 项目管理大纲体系管理风险指标
 *
 * @author tianhelin
 * @date 2025/6/13
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OutlineProjectRiskRequest extends BasePageEntity {
    private String id;
    private String riskId;
    private Integer oprSort;
    /**
     * 项目管理大纲体系目录节点id
     */
    private String oplId;
    private Date createTime;
    private String createUid;
    private String createUname;
}
