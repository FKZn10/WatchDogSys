package com.jpmsoft.fileUpload.pojo.dto.beijing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MaterialDeviceTestReportDTO {

    // 标题
    String title;

    // 资料编号
    String dataCode;

    // 试验编号
    String experimentCode;

    // 委托编号
    String entrustCode;

    // 项目名称
    String projectName;

    // 合同名称
    String contractName;

    // 使用部位
    String usePart;

    // 委托单位
    String entrustUnitName;

    // 委托人
    String entrustManName;

    // 施工单位
    String constructionUnitName;

    // 试样编号
    String sampleCode;

    // 见证人单位
    String witnessUnitName;

    // 见证人
    String witnessName;

    // 建设单位
    String buildUnitName;

    // 设计单位
    String designUnitName;

    // 监理单位
    String superviseUnitName;

    // 委托日期
    Date entrustDate;

    // 检测日期
    Date testDate;

    // 检测项目及要求
    String testProjectAndRequirement;

    // 检测原因
    String testReason;

    // 其他说明
    String otherExplain;

    // 检测结果
    String testResult;

    // 结论
    String conclusion;

    // 备注
    String remark;

    // 批准
    String ratify;

    // 审核
    String audited;

    // 主检
    String mainInspection;

    // 检测机构
    String testUnitName;

    // 报告日期
    Date reportDate;

    // 附件链接
    String filesLink;
    
}
