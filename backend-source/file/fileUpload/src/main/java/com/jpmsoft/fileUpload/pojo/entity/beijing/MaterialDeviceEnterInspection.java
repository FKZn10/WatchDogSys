package com.jpmsoft.fileUpload.pojo.entity.beijing;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.util.List;

@TableName("material_device_enter_inspection")
@Data
public class MaterialDeviceEnterInspection {

    // 主键id
    @TableId(type = IdType.INPUT)
    String uuid;

    // 标题
    String title;

    // 资料编号
    String code;

    // 项目名称
    String projectName;

    // 项目id
    String projectId;

    // 合同名称
    String contractName;

    // 合同id
    String contractId;

    // 进场日期
    Date enterDate;

    // 施工单位
    String constructionUnitName;

    // 施工id
    String constructionUnitId;

    // 分包id
    String subPackageUnitId;

    // 分包单位
    String subPackageUnitName;

    // 施工单位意见
    String constructionUnitCheckOpinion;

    // 监理单位检查意见
    String superviseUnitExamineOpinion;

    // 施工单位负责人
    String constructionUnitDirectorName;

    // 分包单位材料验收负责人
    String subpackageUnitMaterialAcceptanceDirectorName;

    // 专业监理工程师
    String supervisorEngineerName;

    // 附件
    String filesRelationId;

    //制表日期
    Date fillDate;

    // 子表
    @TableField(exist = false)
    List<MaterialDeviceEnterInspectionChild> childList;

}
