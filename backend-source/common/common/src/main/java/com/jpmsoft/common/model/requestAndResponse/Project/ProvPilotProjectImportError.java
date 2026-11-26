package com.jpmsoft.common.model.requestAndResponse.Project;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 省试点项目 逐行错误明细
 *
 * <p>fieldKey 用于 File 服务侧定位列号：annualYear(A列)、projectName(B列)、projectType(C列)、
 * district(D列)、declareUnit(E列)、relatedCityProjectName(F列)。</p>
 *
 * @author whjp
 * @since 2026-09-16
 */
@Data
@ApiModel(value = "省试点项目导入错误明细", description = "导入逐行错误")
public class ProvPilotProjectImportError {

    @ApiModelProperty(value = "Excel 行号（从 1 开始）")
    private Integer rowNum;

    @ApiModelProperty(value = "出错的字段 key，用于定位列号（annualYear→A、projectName→B、projectType→C、district→D、declareUnit→E、relatedCityProjectName→F）")
    private String fieldKey;

    @ApiModelProperty(value = "错误原因文案（已拼好\"第N行第X列\"前缀）")
    private String message;
}