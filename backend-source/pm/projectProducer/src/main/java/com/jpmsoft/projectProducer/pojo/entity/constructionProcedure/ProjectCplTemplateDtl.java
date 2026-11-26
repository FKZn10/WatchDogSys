package com.jpmsoft.projectProducer.pojo.entity.constructionProcedure;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 建设程序项目模板详情
 */
@Data
@TableName("Project_Cpl_Template_Dtl")
public class ProjectCplTemplateDtl {

    @TableId(type = IdType.INPUT)
    private String cpltdId;
    private String cpltId;
    private String cpltdName;
    private String cpltdCode;
    private String cpltdParentId;
    private String cpltdType;
    private Integer cpltdDays;
    private String cpltdProId;
    private String cpltdAdministration;
    private String cpltdAdministrationUrl;
    private String cpltdRemark;
    private String cpltdHtml;
    private String cpltdCplId;
    private Integer cpltdSort;
    private String cpltdAdministrationUrlH;

    private String cpltdFileType;

}
