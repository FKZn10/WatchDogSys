package com.jpmsoft.common.model.requestAndResponse.Project;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 省试点项目 导入结果
 *
 * @author whjp
 * @since 2026-09-16
 */
@Data
@ApiModel(value = "省试点项目导入结果", description = "导入返回的统计结果与逐行错误明细")
public class ProvPilotProjectImportResult {

    @ApiModelProperty(value = "成功导入条数")
    private Integer successCount = 0;

    @ApiModelProperty(value = "跳过条数（历史同名项目，不重复导入）")
    private Integer skipCount = 0;

    @ApiModelProperty(value = "失败条数")
    private Integer failCount = 0;

    @ApiModelProperty(value = "逐行错误明细")
    private List<ProvPilotProjectImportError> errors = new ArrayList<>();
}