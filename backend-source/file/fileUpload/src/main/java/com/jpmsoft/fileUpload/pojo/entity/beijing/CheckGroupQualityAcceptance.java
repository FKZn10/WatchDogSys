package com.jpmsoft.fileUpload.pojo.entity.beijing;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 检验批质量验收表
 */
@TableName("check_group_quality_acceptance")
@Data
public class CheckGroupQualityAcceptance {

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
    String unitEngineeringName;

    // 分部（子分部）工程名称
    String branchEngineeringName;

    // 分项工程名称
    String subItemEngineeringName;

    // 施工单位id
    String constructionUnitId;

    // 施工单位
    String constructionUnitName;

    // 项目负责人
    String projectDirectorName;

    // 检验批容量
    Double checkGroupCapacity;

    // 分包单位id
    String subpackageUnitId;

    // 分包单位
    String subpackageUnitName;

    // 分包单位项目负责人
    String subpackageUnitProjectDirectorName;

    // 分包单位项目负责人id
    String subpackageUnitProjectDirectorId;

    // 检验批部位
    String checkGroupPart;

    // 施工依据
    String constructionBasis;

    // 验收依据
    String acceptanceBasis;

    // 施工单位检查结果
    String constructionUnitCheckResult;

    // 专业工长id
    String professionalForemanId;

    // 专业工长
    String professionalForemanName;

    // 项目专业质量检查员id
    String projectProfessionalQualityCheckerId;

    // 项目专业质量检查员
    String projectProfessionalQualityCheckerName;

    // 项目专业质量检查员签字日期
    Date projectProfessionalQualityCheckerSignDate;

    // 监理单位验收结论
    String superviseUnitAcceptanceConclusion;

    // 专业监理工程师id
    String professionalSuperviseEngineerId;

    // 专业监理工程师
    String professionalSuperviseEngineerName;

    // 专业监理工程师签字日期
    Date professionalSuperviseEngineerSignDate;

    // 附件
    String filesRelationId;

    @TableField(exist = false)
    List<CheckGroupQualityAcceptanceChild> childList;

}
