package com.jpmsoft.fileUpload.pojo.entity.beijing;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 分部工程验收 记录
 */
@TableName("branch_project_quality_accept")
@Data
public class BranchProjectQualityAccept {

    // 主键id
    String uuid;

    // 标题
    String title;

    // 编号
    String code;

    // 项目id
    String projectId;

    // 项目名称
    String projectName;

    // 合同id
    String contractId;

    // 合同名称
    String contractName;

    // 单位（子单位）工程名称
    String unitProjectName;

    // 子分部工程数量
    String subEngineeringNumber;

    // 分项工程数量
    String subItemEngineeringNumber;

    // 施工单位id
    String constructionUnitId;

    // 施工单位
    String constructionUnitName;

    // 项目负责人
    String projectDirectorName;

    // 技术（质量）负责人
    String techDirectorName;

    // 分包单位
    String subpackageUnitName;

    // 分包单位项目负责人
    String subpackageUnitProjectDirectorName;

    // 分包内容
    String subpackageContent;

    // 质量控制资料
    String qualityControlData;

    // 安全和功能检验结果
    String securityAndFunctionTestResult;

    // 观感质量检查结果
    String appearanceQualityCheckResult;

    // 综合验收结论
    String comprehensiveAcceptanceConclusion;

    // 附件上传
    String filesRelationId;

    // 施工单位项目负责人
    String constructionUnitProjectDirectorName;

    // 施工单位项目负责人签字日期
    Date constructionUnitProjectDirectorSignDate;

    // 勘察单位项目负责人
    String surveyUnitProjectDirectorName;

    // 勘察单位项目负责人签字日期
    Date surveyUnitProjectDirectorSignDate;

    // 设计单位项目负责人
    String designUnitProjectDirectorName;

    // 设计单位项目负责人签字日期
    Date designUnitProjectDirectorSignDate;

    // 监理单位总监理工程师
    String superviseUnitChiefSuperviseEngineerName;

    // 监理单位总监理工程师签字日期
    Date superviseUnitChiefSuperviseEngineerSignDate;

    @TableField(exist = false)
    List<BranchProjectQualityAcceptChild> childList;

}
