package com.jpmsoft.fileUpload.pojo.entity.beijing;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


/**
 * 分部工程验收子表
 */
@TableName("branch_project_quality_accept_child")
@Data
public class BranchProjectQualityAcceptChild {

    // 主键id
    private String uuid;

    // 分部工程验收id
    private String branchProjectQualityAcceptId;

    // 子分部工程名称
    private String subBranchEngineeringName;

    // 分项工程名称
    private String subItemEngineeringName;

    // 检验批数量
    private String checkGroupNumber;

    // 施工单位检查结果
    private String constructionUnitCheckResult;

    // 监理单位验收结论
    private String superviseUnitAcceptanceConclusion;

}
