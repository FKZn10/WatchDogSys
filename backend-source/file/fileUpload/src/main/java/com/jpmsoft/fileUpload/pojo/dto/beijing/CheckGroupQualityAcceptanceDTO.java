package com.jpmsoft.fileUpload.pojo.dto.beijing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 检验批质量验收表
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CheckGroupQualityAcceptanceDTO {

    // 主键id
    String uuid;

    // 标题
    String title;

    // 编号
    String code;

    // 项目名称
    String projectName;

    // 合同名称
    String contractName;

    // 单位（子单位）工程名称
    String unitEngineeringName;

    // 分部（子分部）工程名称
    String branchEngineeringName;

    // 分项工程名称
    String subItemEngineeringName;

    // 施工单位
    String constructionUnitName;

    // 项目负责人
    String projectDirectorName;

    // 检验批容量
    String checkGroupCapacity;

    // 分包单位
    String subpackageUnitName;

    // 分包单位项目负责人
    String subpackageUnitProjectDirectorName;

    // 检验批部位
    String checkGroupPart;

    // 施工依据
    String constructionBasis;

    // 验收依据
    String acceptanceBasis;

    // 施工单位检查结果
    String constructionUnitCheckResult;

    // 专业工长
    String professionalForemanName;

    // 项目专业质量检查员
    String projectProfessionalQualityCheckerName;

    // 项目专业质量检查员签字日期
    Date projectProfessionalQualityCheckerSignDate;

    // 监理单位验收结论
    String superviseUnitAcceptanceConclusion;

    // 专业监理工程师
    String professionalSuperviseEngineerName;

    // 专业监理工程师签字日期
    Date professionalSuperviseEngineerSignDate;

    // 附件
    String filesLink;

    List<CheckGroupQualityAcceptanceChildDTO> childDTOList;

}
