package com.jpmsoft.projectProducer.pojo.entity.riskWarn;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 风险预警信息
 */
@Data
@TableName("Risk_Warn")
public class RiskWarn {

    @TableId(type = IdType.INPUT)
    private String rwId;
    private String rwiId;
    private String pId;
    private String secId;
    private String conId;
    private String rwCode;
    private String rwPmId;
    private Date rwTime;
    private String dId;
    private String rwContent;
    private String rwFrom;
    private String rwBusinessInforKey;
    private String rwFlowActName;
    private String rwFlowUid;
    private String rwFlowUname;
    private String rwFlowUphone;
    private String rwFlowCId;
    private Date rwFlowReceiveTime;
    private Date rwFlowApprovalTime;
    private Date rwFlowEtlTime;
    private String rwHandleUid;
    private String rwHandleUname;
    private Date rwHandleTime;
    private String rwHandleContent;
    /**
     * null或者0 未生成流程
     * 1 已生成流程
     */
    private Integer rwHaveFlow;
    private Integer rwState;
    private Date rwStateTime;
    private String teId;

    /** 数据快照列定义 JSON（前端 Ant Design 列配置，后端原样透传） */
    private String rwSnapshotColumns;

    /** 数据快照行数据 JSON（每行为平铺 key-value 对象，后端原样透传） */
    private String rwSnapshotRows;

    /** 数据快照总行数 */
    private Integer rwSnapshotTotalRows;
}
