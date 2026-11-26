package com.jpmsoft.fileUpload.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 项目建设程序库
 */
@Data
@TableName("Project_Cp_Library")
public class ProjectCpLibrary {
    @TableId(type = IdType.INPUT)
    private String cplId;
    private String cplName;
    private String cplCode;
    private String cplParentId;
    private String cplType;
    private Integer cplDays;
    /**
     * 前置建设程序
     */
    private String cplProId;
    /**
     * 行政部门
     */
    private String cplAdministration;
    /**
     * 官网链接
     */
    private String cplAdministrationUrl;
    private String cplRemark;
    /**
     * 表单
     */
    private String cplHtml;
    private String cplCreateUid;
    private String cplCreateUname;
    private Date cplCreateTime;
    private String cplState;
    private Integer cplSort;
    private String cplAdministrationUrlH;
    private String tCode;

    private String cplFileExpiain;
    private String cplAchievementType;
}
