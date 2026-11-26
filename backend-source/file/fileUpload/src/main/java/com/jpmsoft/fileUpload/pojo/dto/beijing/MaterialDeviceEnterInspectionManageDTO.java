package com.jpmsoft.fileUpload.pojo.dto.beijing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MaterialDeviceEnterInspectionManageDTO implements Serializable {

    // 标题
    String title;

    // 资料编号
    String code;

    // 项目名称
    String projectName;

    // 合同名称
    String contractName;

    // 进场日期
    Date enterDateStr;

    // 施工单位
    String constructionUnitName;

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

    // 文件链接地址（相对路径）
    String filesLink;

    //制表日期
    Date fillDate;

    // 子表
    List<MaterialDeviceEnterInspectionManageChildDTO> childDTOList;

}
