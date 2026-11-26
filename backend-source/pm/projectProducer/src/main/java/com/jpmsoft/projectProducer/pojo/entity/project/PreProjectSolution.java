package com.jpmsoft.projectProducer.pojo.entity.project;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("Pre_Project_Solution")
public class PreProjectSolution extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 4811912022059289979L;
    private String id;
    private String versionId;
    private Integer lastest;
    private String projectId;
    private String projectRegisterInfoId;
    private String remark;
    private String declarer;
    private BigDecimal selfScore;
    private BigDecimal scoreReal;
    private Date declarationTime;

    /**
     *
     */
    private String auditStatus;

    /**
     *
     */
    private String auditComment;
    private String solutionFileId;
}
