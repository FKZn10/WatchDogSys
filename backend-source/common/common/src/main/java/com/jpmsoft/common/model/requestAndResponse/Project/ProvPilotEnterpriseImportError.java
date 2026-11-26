package com.jpmsoft.common.model.requestAndResponse.Project;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 省试点企业 逐行错误明细
 *
 * @author whjp
 * @since 2026-09-17
 */
@Data
@ApiModel(value = "省试点企业导入错误明细", description = "导入逐行错误")
public class ProvPilotEnterpriseImportError {

    @ApiModelProperty(value = "Excel 行号（从 1 开始）")
    private Integer rowNum;

    @ApiModelProperty(value = "出错的字段 key（annualYear→B、batch→C、enterpriseName→D、enterpriseType→E、district→F）")
    private String fieldKey;

    @ApiModelProperty(value = "错误原因文案（file 服务拼好\"第N行第X列\"前缀）")
    private String message;
}