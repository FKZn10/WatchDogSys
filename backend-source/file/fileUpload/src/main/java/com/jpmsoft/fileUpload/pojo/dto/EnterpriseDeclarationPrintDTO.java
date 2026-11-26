package com.jpmsoft.fileUpload.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 企业申报表打印入参
 * <p>
 * 前端把整条企业申报数据作为对象 POST 过来，后端<b>不查库</b>，按传来值原样渲染模板生成 PDF。
 * 字段名对应表 Enterprise_Declaration 的 columnCode 小驼峰；字典翻译（如申报类型 02→市试点）由前端完成，
 * 后端只渲染展示值。
 * <p>
 * 全部为新增代码，不改动任何原有逻辑。
 *
 * @author 企业申报表打印
 */
@Data
@ApiModel(value = "企业申报表打印入参")
public class EnterpriseDeclarationPrintDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("申报年度")
    private String declareYear;

    @ApiModelProperty("申报类型（展示值；字典 JPMsoft_XMSBSBLX：01 常规 / 02 市试点 / 03 省试点，译文由前端完成）")
    private String declareType;

    @ApiModelProperty("申报目的（展示值）")
    private String declarePurpose;

    @ApiModelProperty("申报专业（展示值）")
    private String declareMajor;

    @ApiModelProperty("申报联系人")
    private String declareContact;

    @ApiModelProperty("联系人电话")
    private String contactPhone;

    @ApiModelProperty("企业名称")
    private String companyName;

    @ApiModelProperty("统一社会信用代码")
    private String creditCode;

    @ApiModelProperty("企业类型（展示值）")
    private String companyType;

    @ApiModelProperty("企业资质")
    private String companyQualification;

    @ApiModelProperty("注册资本；单位（元/万元）与是否格式化待确认，当前原样输出")
    private BigDecimal registeredCapital;

    @ApiModelProperty("法定代表人")
    private String legalPerson;

    @ApiModelProperty("法定代表人电话")
    private String legalPersonPhone;

    @ApiModelProperty("注册地址")
    private String registeredAddress;

    @ApiModelProperty("办公生产地址")
    private String officeAddress;

    @ApiModelProperty("企业概况")
    private String companyProfile;

    @ApiModelProperty("项目名称；模板中暂无对应栏位，当前不渲染（是否需要呈现待确认）")
    private String pName;

    @ApiModelProperty("上传申报表盖章扫描件（附件 id）；模板中暂无对应栏位，当前不渲染（是否需要盖章图待确认）")
    private String sealScanFile;
}
