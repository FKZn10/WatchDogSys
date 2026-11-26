package com.jpmsoft.common.model.request.challengePlan;

import com.jpmsoft.common.model.BasePageEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * @author tianhelin
 * @date 2025/6/4
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ChallengePlanMonthlyRequest extends BasePageEntity {
    private String id;
    private String createUid;
    private String createUname;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    private String cpaId;
    private String declareYear;
    private String declareMonth;
    private String executionStatus;
    private String specificProgress;
    private String fileRelationId;
    private String fileIds;

    private String orRemarks;
    private List<String> ids;
}
