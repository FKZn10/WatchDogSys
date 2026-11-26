package com.jpmsoft.common.model.request.indicatorData;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class IndicatorDataParamRequest {



    /**
     * 项目id
     */
    private String projectId;

    /**
     * 合同id
     */
    private String contractId;

    /**
     * 评价类型
     */
    private String evaluateType;

    /**
     * 评价年度
     */
    private Integer evaluationYear;

    /**
     * 评价季度
     */
    private Integer evaluationQuarter;

    /**
     * 评价阶段ID
     */
    private String evaluationStage;

    /**
     * 评价阶段名称
     */
    private String evaluationStageStr;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 报备类型 01：报备 02：变更
     */
    private String rpType="02";

    /**
     * 岗位
     */
    private List<String> postionNameList=new ArrayList<>();

}
