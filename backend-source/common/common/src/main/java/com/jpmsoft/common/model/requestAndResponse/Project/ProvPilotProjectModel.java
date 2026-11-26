package com.jpmsoft.common.model.requestAndResponse.Project;

import com.jpmsoft.common.base.BaseFileDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 省试点项目 请求/响应模型
 *
 * @author whjp
 * @since 2026-09-16
 */
@Data
@ApiModel(value = "省试点项目模型", description = "省试点项目 编辑/列表/详情/导出 请求与响应模型")
public class ProvPilotProjectModel extends BaseFileDto {

    @ApiModelProperty(value = "所属年度（即年份）", example = "2026")
    private Integer annualYear;

    @ApiModelProperty(value = "批次")
    private String batch;

    @ApiModelProperty(value = "所属年度范围查询：列表 [起始年度, 截止年度]，第0个开始、第1个结束", example = "[\"2024\",\"2026\"]")
    private List<String> annualYearList;

    @ApiModelProperty(value = "项目名称")
    private String projectName;

    @ApiModelProperty(value = "项目类型（非字典）：房屋建筑=01、装饰装修=02、市政工程=03")
    private String projectType;

    @ApiModelProperty(value = "项目类型多选筛选（列表查询用，传数据库编码 01/02/03，in 查询）")
    private List<String> projectTypeList;

    @ApiModelProperty(value = "是否关联市项目：01=是、00=否")
    private String hasRelatedCityProject;

    @ApiModelProperty(value = "项目编号（自动生成）")
    private String projectCode;

    @ApiModelProperty(value = "所在区（原样存储，不用字典）")
    private String district;

    @ApiModelProperty(value = "所在区多选筛选（列表查询用，in 查询）")
    private List<String> districtList;

    @ApiModelProperty(value = "申报单位")
    private String declareUnit;

    @ApiModelProperty(value = "关联市项目名称（对应项目申报 project_register_info.name）")
    private String relatedCityProjectName;

    @ApiModelProperty(value = "关联市项目 id（对应项目申报 project_register_info.id，一对一）")
    private String relatedCityProjectId;

    @ApiModelProperty(value = "预留文件 id（前端不传，后端导入成功后生成）")
    private String fileId;

    @ApiModelProperty(value = "修改人 id")
    private String updateUid;

    @ApiModelProperty(value = "修改人名称")
    private String updateUname;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;
}