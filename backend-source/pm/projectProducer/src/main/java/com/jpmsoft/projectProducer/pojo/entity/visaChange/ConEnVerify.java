package com.jpmsoft.projectProducer.pojo.entity.visaChange;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 工程量核实
 */
@Data
@TableName("Con_En_Verify")
public class ConEnVerify {
    @TableId(type = IdType.INPUT)
    private String envId;
    /**
     * 工程洽商施工方案及预算ID
     */
    private String enbId;
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
    private String envCode;
    /**
     * 工程名称
     */
    private String envProjectName;
    /**
     * 施工单位ID
     */
    private String envContractorId;
    /**
     * 时间
     */
    private Date envDate;
    /**
     * 工程量说明
     */
    private String envRemark;
    private String envCreateUid;
    private String envCreateUname;
    private Date envCreateTime;
    private Integer envState;
    private Date envStateTime;
    private String enisId;
    private String teId;
}
