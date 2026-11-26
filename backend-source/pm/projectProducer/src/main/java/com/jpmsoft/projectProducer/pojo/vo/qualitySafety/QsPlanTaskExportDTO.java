package com.jpmsoft.projectProducer.pojo.vo.qualitySafety;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class QsPlanTaskExportDTO {

    @Excel(name = "流程编码", width = 20)
    private String ptCode;
    @Excel(name = "项目名称", width = 20)
    private String pName;

    @Excel(name = "检查类型", width = 20)
    private String ptTypeStr;

    @Excel(name = "检查日期", width = 20,format = "yyyy-MM-dd")
    private Date createTime;

    @Excel(name = "检查人员", width = 20)
    private String createUname;

    @Excel(name = "施工单位", width = 20)
    private String ptContractorName;

    @Excel(name = "监理单位", width = 20)
    private String ptSupervisorName;

    @Excel(name = "流程状态", width = 20)
    private String stateStr;

    /**
     * 问题数量
     */
    @Excel(name = "问题数量", width = 20)
    private Integer qsRecordCount = 0;
    /**
     * 整改数量
     */
    @Excel(name = "整改数量", width = 20)
    private Integer qsFinishCount = 0;

    /**
     * 闭合率
     */
    @Excel(name = "闭合率", width = 20)
    private BigDecimal qsFinishRate;
    /**
     * 回复率
     */
    @Excel(name = "回复率", width = 20)
    private BigDecimal qsReplyRate;
}
