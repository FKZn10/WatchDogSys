package com.jpmsoft.fileUpload.pojo.entity.beijing;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 检验批质量验收子表
 */
@TableName("check_group_quality_acceptance_child")
@Data
public class CheckGroupQualityAcceptanceChild {

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
