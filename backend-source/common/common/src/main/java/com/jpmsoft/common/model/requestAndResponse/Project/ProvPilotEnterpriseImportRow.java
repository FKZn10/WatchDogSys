package com.jpmsoft.common.model.requestAndResponse.Project;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 省试点企业 导入行（File 服务解析 Excel 后逐行传给 PP）
 *
 * <p>对应模板列（第 4 行起为数据）：A=序号、B=年度、C=批次、D=企业名称、E=企业类型、F=所在区。</p>
 *
 * @author whjp
 * @since 2026-09-17
 */
@Data
@ApiModel(value = "省试点企业导入行", description = "导入 Excel 的逐行原始数据")
public class ProvPilotEnterpriseImportRow {

    @ApiModelProperty(value = "该行在 Excel 中的行号（从 1 开始），仅用于错误定位")
    private Integer excelRowNum;

    @ApiModelProperty(value = "年度（B 列）")
    private String colAnnualYear;

    @ApiModelProperty(value = "批次（C 列）")
    private String colBatch;

    @ApiModelProperty(value = "企业名称（D 列）")
    private String colEnterpriseName;

    @ApiModelProperty(value = "企业类型（E 列，字典名称）")
    private String colEnterpriseType;

    @ApiModelProperty(value = "所在区（F 列，字典名称）")
    private String colDistrict;
}