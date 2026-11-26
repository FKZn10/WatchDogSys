package com.jpmsoft.common.model.request.projectOutline;

import com.jpmsoft.common.model.BasePageEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 体系模板风险指标
 *
 * @author tianhelin
 * @date 2025/6/11
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OutlineLibRiskRequest extends BasePageEntity {
    private String id;
    /**
     * 体系模板-目录节点ID
     */
    private String oslId;
    private String riskId;
    private Integer olrSort;
    private Date createTime;
    private String createUid;
    private String createUname;
}
