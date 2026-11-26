package com.jpmsoft.common.model.requestAndResponse.Project;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 省试点项目 导入请求
 *
 * <p>导入接口只接收 file + token，不再有 tCode / tId 等流程字段；本请求体由 file 服务解析 Excel 后组装，
 * 透传创建人/租户，创建时间/状态/状态时间由 PP 侧以导入时间、状态 0 落地。</p>
 *
 * @author whjp
 * @since 2026-09-16
 */
@Data
@ApiModel(value = "省试点项目导入请求", description = "导入请求体（file 服务解析 Excel 后组装）")
public class ProvPilotProjectImportRequest {

    @ApiModelProperty(value = "导入行数据")
    private List<ProvPilotProjectImportRow> rows;

    @ApiModelProperty(value = "租户 id")
    private String teId;

    @ApiModelProperty(value = "创建人 id")
    private String createUid;

    @ApiModelProperty(value = "创建人名称")
    private String createUname;

    @ApiModelProperty(value = "预留文件 id（file 服务上传 Excel 源文件后回传，整批共用；sys_fileannex 中可查）")
    private String fileId;
}