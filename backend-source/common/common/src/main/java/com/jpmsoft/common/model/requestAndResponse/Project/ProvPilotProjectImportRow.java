package com.jpmsoft.common.model.requestAndResponse.Project;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 省试点项目 导入行（File 服务解析 Excel 后逐行传给 PP）
 *
 * <p>excelRowNum 为 Excel 中的原始行号（从 1 开始，含表头），由 File 服务在错误回显时使用。</p>
 * <p>对应模板列（第 4 行起为数据）：
 * A=序号、B=年度、C=批次、D=项目名称、E=项目类型、F=所在区、G=申报单位。</p>
 *
 * @author whjp
 * @since 2026-09-16
 */
@Data
@ApiModel(value = "省试点项目导入行", description = "导入 Excel 的逐行原始数据")
public class ProvPilotProjectImportRow {

    @ApiModelProperty(value = "该行在 Excel 中的行号（从 1 开始），仅用于错误定位")
    private Integer excelRowNum;

    @ApiModelProperty(value = "年度（B 列）")
    private String colAnnualYear;

    @ApiModelProperty(value = "批次（C 列）")
    private String colBatch;

    @ApiModelProperty(value = "项目名称（D 列）")
    private String colProjectName;

    @ApiModelProperty(value = "项目类型（E 列，名称：房屋建筑/装饰装修/市政工程）")
    private String colProjectType;

    @ApiModelProperty(value = "所在区（F 列，原样存储）")
    private String colDistrict;

    @ApiModelProperty(value = "申报单位（G 列）")
    private String colDeclareUnit;
}