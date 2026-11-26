package com.jpmsoft.projectProducer.pojo.entity.project;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目建议书(项目信息源)
 */
@Data
@TableName("Pre_Pro_Proposal")
public class PreProProposal {

    @TableId(type = IdType.INPUT)
    private String ppId;
    //项目建议书名称
    private String ppName;
    //项目编码
    private String ppCode;
    //项目名称
    private String ppProName;
    //申报投资估算
    private BigDecimal ppInvestment;
    //可研编制单位ID
    private String ppFeasibilityUnitId;
    //估算审核单位ID
    private String ppEstimationUnitId;
    //估算清单文件上传
    private String ppInvestmentFile;
    private String ppRemark;
    private String ppCreateUid;
    private String ppCreateUname;
    private Date ppCreateTime;
    private Integer ppState;
    private Date ppStateTime;
    //项目类型
    private Integer ppType;
    //是否有监理单位参与
    private Integer ppIsParticipate;
    //项目所属部门
    private String ppDepartment;
    private String teId;
}
