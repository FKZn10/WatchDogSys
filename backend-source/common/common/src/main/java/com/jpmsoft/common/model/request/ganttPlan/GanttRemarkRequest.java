package com.jpmsoft.common.model.request.ganttPlan;

import com.jpmsoft.common.model.BasePageEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author tianhelin
 * @date 2025/5/26
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GanttRemarkRequest extends BasePageEntity {
    private String gmsId;
    private String remark;
}
