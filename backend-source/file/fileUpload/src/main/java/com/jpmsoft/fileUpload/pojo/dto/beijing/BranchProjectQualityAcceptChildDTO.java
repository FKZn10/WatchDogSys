package com.jpmsoft.fileUpload.pojo.dto.beijing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 分部工程验收子表
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BranchProjectQualityAcceptChildDTO {

    // 分部工程验收id
    private String branchProjectQualityAcceptId;

    // 子分部工程名称
    private String subBranchEngineeringName;

    // 分项工程名称
    private String subItemEngineeringName;

    // 检验批数量
    private Integer checkGroupNumber;

    // 施工单位检查结果
    private String constructionUnitCheckResult;

    // 监理单位验收结论
    private String superviseUnitAcceptanceConclusion;

}
