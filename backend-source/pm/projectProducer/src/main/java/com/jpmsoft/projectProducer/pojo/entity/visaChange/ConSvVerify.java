package com.jpmsoft.projectProducer.pojo.entity.visaChange;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 现场签证工程量核实
 */
@Data
@TableName("Con_Sv_Verify")
public class ConSvVerify {
    @TableId(type = IdType.INPUT)
    private String svvId;
    /**
     * 现场签证指令单ID
     */
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
    private String svvCode;
    /**
     * 工程名称
     */
    private String svvProjectName;
    /**
     * 施工单位ID
     */
    private String svvContractorId;
    /**
     * 时间
     */
    private Date svvDate;
    /**
     * 工程量说明
     */
    private String svvRemark;
    private String svvCreateUid;
    private String svvCreateUname;
    private Date svvCreateTime;
    private Integer svvState;
    private Date svvStateTime;
    private String teId;
}
