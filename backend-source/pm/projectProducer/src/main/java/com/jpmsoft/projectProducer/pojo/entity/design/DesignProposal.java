package com.jpmsoft.projectProducer.pojo.entity.design;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目建议书
 */
@Data
@TableName("Design_Proposal")
public class DesignProposal {

    @TableId(type = IdType.INPUT)
    private String dpId;
    //项目建议书名称
    private String dpName;
    //项目ID
    private String pId;
    //申报投资估算
    private BigDecimal dpInvestment;
    //可研编制单位ID
    private String dpFeasibilityUnitId;
    //估算审核单位ID
    private String dpEstimationUnitId;
    //估算清单文件上传
    private String dpInvestmentFile;
    private String dpRemark;
    private String dpCreateUid;
    private String dpCreateUname;
    private Date dpCreateTime;
    private Integer dpState;
    private Date dpStateTime;
    private String teId;
    //合同Id
    private String conId;
    //项目建议书编号
    private String dpCode;
    //可研编制单位联系方式
    private String dpFeasibilityUnitTel;
    //编制时间
    private Date dpCompilationTime;
}
