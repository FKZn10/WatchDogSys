package com.jpmsoft.projectProducer.pojo.entity.challengePlan;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author tianhelin
 * @date 2025/5/30
 */
@Data
public class ChallengePlanMonthly {
    @TableId(type = IdType.INPUT)
    private String id;
    private String createUid;
    private String createUname;
    private Date createTime;
    private Integer state;
    private Date stateTime;

    /**
     * 年度计划表id
     */
    private String cpaId;
    private String declareYear;
    private String declareMonth;

    /**
     * 执行状态
     */
    private String executionStatus;

    /**
     * 具体进展
     */
    private String specificProgress;

    private String fileRelationId;
}