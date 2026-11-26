package com.jpmsoft.projectProducer.pojo.entity.project;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 项目人员报备详情
 */
@Data
@TableName("Reported_Personnel_Dtl")
public class ReportedPersonnelDtl {

    @TableId(type = IdType.INPUT)
    private String rpdId;
    private String rpId;
    private String rpdPdid;
    private String rpdPpid;
    private String rpdUid;
    private String rpdType;
    private String rpdCpy;
    private String rpdName;
    private String rpdNumber;
    private String rpdTel;
    private Integer rpdSort;
    private String rpdQualification;
    private String pupId;
    private Boolean rpdAdd;
    private String ownerUnit;
    private String rpdRemark;
    private String oldUid;
    private String oldName;
}
