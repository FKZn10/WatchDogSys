package com.jpmsoft.common.model.requestAndResponse.Project;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 省试点企业 导入请求（file 服务解析 Excel 后组装）
 *
 * @author whjp
 * @since 2026-09-17
 */
@Data
@ApiModel(value = "省试点企业导入请求", description = "导入请求体（file 服务解析 Excel 后组装）")
public class ProvPilotEnterpriseImportRequest {

    @ApiModelProperty(value = "导入行数据")
    private List<ProvPilotEnterpriseImportRow> rows;

    @ApiModelProperty(value = "租户 id")
    private String teId;

    @ApiModelProperty(value = "创建人 id")
    private String createUid;

    @ApiModelProperty(value = "创建人名称")
    private String createUname;
}