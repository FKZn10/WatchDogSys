package com.jpmsoft.projectProducer.pojo.entity.visaChange;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 工程洽商指令单
 */
@Data
@TableName("Con_Enis")
public class ConEnis {
    @TableId(type = IdType.INPUT)
    private String enisId;
    /**
     * 项目ID
     */
    private String pId;
    /**
     * 合同ID
     */
    private String conId;
    /**
     * 编号
     */
    private String enisCode;
    /**
     * 工程名称
     */
    private String enisProjectName;
    /**
     * 提出单位ID
     */
    private String enisProposedId;
    /**
     * 专业名称
     */
    private String enisMajor;
    /**
     * 图号
     */
    private String enisDrawingNo;
    /**
     * 时间
     */
    private Date enisDate;
    /**
     * 内容概要
     */
    private String enisContentSummary;
    /**
     * 详细内容
     */
    private String enisContent;
    /**
     * 洽商单分类字典
     */
    private String enisType;
    /**
     * 工程部位
     */
    private String enisLocation;
    private String enisCreateUid;
    private String enisCreateUname;
    private Date enisCreateTime;
    private Integer enisState;
    private Date enisStateTime;
    private String enisDep;
    private Date enisFinishDate;
    private String enisName;
    private String enisApprovalMatter;
    private String teId;
    private String enisCategory;
    private BigDecimal enisContractorAmt;
    private String enisMainCode;
    private String enisSubCode;
    private BigDecimal enisHbMoney;
    private String enisHbRemark;
    private String enisHbCheck;
    /**
     * 监理预算
     */
    private BigDecimal enisSupervisionAmt;
}
