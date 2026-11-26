package com.jpmsoft.projectProducer.pojo.entity.visaChange;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 现场签证指令单
 */
@Data
@TableName("Con_Svis")
public class ConSvis {

    @TableId(type = IdType.INPUT)
    private String svisId;
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
    private String svisCode;
    /**
     * 工程名称
     */
    private String svisProjectName;
    /**
     * 提出单位ID
     */
    private String svisProposedId;
    /**
     * 专业名称字典
     */
    private String svisMajor;
    /**
     * 图号
     */
    private String svisDrawingNo;
    /**
     * 时间
     */
    private Date svisDate;
    /**
     * 内容摘要
     */
    private String svisContentSummary;
    /**
     * 详细内容
     */
    private String svisContent;
    private String svisCreateUid;
    private String svisCreateUname;
    private Date svisCreateTime;
    private Integer svisState;
    private Date svisStateTime;
    private String svisName;
    private String teId;
    private BigDecimal svisContractorAmt;

    private String svisMainCode;
    private String svisSubCode;
    /**
     * 监理预算
     */
    private BigDecimal svisSupervisionAmt;
}
