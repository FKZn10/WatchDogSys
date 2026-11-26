package com.jpmsoft.fileUpload.pojo.dto.beijing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 分项工程质量验收记录
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SubItemEngineeringQualityAcceptanceDTO {

    // uuid
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

    // 分项工程工程量
    String subItemEngineeringEngineeringQuantity;

    // 检验批数量
    Double checkGroupNumber;

    // 施工单位
    String constructionUnitName;

    // 项目负责人
    String projectDirectorName;

    // 项目技术负责人
    String projectTechDirectorName;

    // 分包单位
    String subpackageUnitName;

    // 分包单位项目负责人
    String subpackageUnitProjectDirectorName;

    // 分包内容
    String subpackageContent;

    // 说明
    String explanation;

    // 施工单位检查结果
    String constructionUnitCheckResult;

    // 项目专业技术负责人
    String projectProfessionalTechDirectorName;

    // 项目专业技术负责人签字日期
    Date projectProfessionalTechDirectorSignDate;

    // 监理单位验收结论
    String superviseUnitAcceptanceConclusion;

    // 专业监理工程师
    String professionalSupervisorName;

    // 专业监理工程师签字日期
    Date professionalSupervisorSignDate;

    // 附件上传
    String filesLink;

    List<SubItemEngineeringQualityAcceptanceChildDTO> childDTOList;

}
