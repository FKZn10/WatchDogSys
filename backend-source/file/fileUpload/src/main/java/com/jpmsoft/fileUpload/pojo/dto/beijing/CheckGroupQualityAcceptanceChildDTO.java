package com.jpmsoft.fileUpload.pojo.dto.beijing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 检验批质量验收子表
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CheckGroupQualityAcceptanceChildDTO {

    // 主键id
    String uuid;

    // 检验批质量验收id
    String checkGroupQualityAcceptanceId;

    // 验收项目类型
    String acceptanceProjectType;

    // 验收项目
    String acceptanceProjectName;

    // 设计要求及规范规定
    String designRequirement;

    // 最小/实际抽样数
    String maximumSamplingNumber;

    // 检查记录
    String checkRecord;

    //检查结果
    String checkResult;

}
