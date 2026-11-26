package com.jpmsoft.fileUpload.pojo.entity.beijing;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分项工程质量验收子表
 */
@TableName("sub_item_engineering_quality_acceptance_child")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SubItemEngineeringQualityAcceptanceChild {

    // 主键id
    @TableId(type = IdType.INPUT)
    String uuid;

    // 分项工程质量验收id
    String subItemEngineeringQualityAcceptanceId;

    // 检验批名称
    String checkGroupName;

    // 检验批容量
    Double checkGroupCapacity;

    // 部位/区段
    String part;

    // 施工单位检查结果
    String constructionUnitCheckResult;

    // 监理单位验收结论
    String superviseUnitAcceptanceConclusion;

}
