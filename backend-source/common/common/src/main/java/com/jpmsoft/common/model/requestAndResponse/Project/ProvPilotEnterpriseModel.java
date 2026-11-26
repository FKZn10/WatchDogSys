package com.jpmsoft.common.model.requestAndResponse.Project;

import com.jpmsoft.common.base.BaseFileDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 省试点企业 请求/响应模型
 *
 * @author whjp
 * @since 2026-09-17
 */
@Data
@ApiModel(value = "省试点企业模型", description = "省试点企业 编辑/列表/详情/导出 请求与响应模型")
public class ProvPilotEnterpriseModel extends BaseFileDto {

    @ApiModelProperty(value = "年度（即年份）", example = "2026")
    private Integer annualYear;

    @ApiModelProperty(value = "批次")
    private String batch;

    @ApiModelProperty(value = "年度范围查询：列表 [起始年度, 截止年度]，第0个开始、第1个结束", example = "[\"2024\",\"2026\"]")
    private List<String> annualYearList;

    @ApiModelProperty(value = "企业名称")
    private String enterpriseName;

    @ApiModelProperty(value = "企业类型（字典码，字典 JPMsoft_ZPSBZY：01=勘察设计、02=建筑施工、03=部品生产、04=智能装备研发制造、05=技术服务）")
    private String enterpriseType;

    @ApiModelProperty(value = "企业类型多选筛选（列表查询用，传数据库编码，in 查询）")
    private List<String> enterpriseTypeList;

    @ApiModelProperty(value = "企业编号（自动生成，SQ+年度4+批次2+序号3）")
    private String enterpriseCode;

    @ApiModelProperty(value = "所在区（字典码，字典 JPMsoft_ZPSZQ）")
    private String district;

    @ApiModelProperty(value = "所在区多选筛选（列表查询用，in 查询）")
    private List<String> districtList;

    @ApiModelProperty(value = "修改人 id")
    private String updateUid;

    @ApiModelProperty(value = "修改人名称")
    private String updateUname;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;
}