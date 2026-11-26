package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 风险预警指标
 */
@Data
@TableName("Risk_Warn_Index")
public class RiskWarnIndex {

    @TableId(type = IdType.INPUT)
    private String rwiId;
    private String rwiCode;
    private String rwiType;
    private String rwiName;
    private String rwiDesc;
    private String rwiProblem;
    private String rwiCalculationRule;
    private String rwiValue;
    private String rwiBasis;
    private String rwiLevel;
    private String rwiCreateUid;
    private String rwiCreateUname;
    private Date rwiCreateTime;
    private Integer rwiState;
    private String dId;
    private String rwiPersonUid;
    private String rwiPersonUname;
    private Integer rwiProjectLeader;
    private String rwiBusinessInforUrl;
    private String rwiScreeningConditions;
    private String rwiQuerySql;
    /** 数据快照 SQL：查询命中预警后的明细数据，用于线索数据快照（columns+rows） */
    private String rwiSnapshotSql;
    /** 责任部门领导 ID（配置后线索直接下发至该领导核实，跳过纪检下发） */
    private String rwiLeaderId;
    /** 责任部门领导姓名 */
    private String rwiLeaderName;
    /** 是否推送到纪检：01 否 / 02 是，空视为 02 */
    private String rwiPushToJw;
    private String rwiCcPost;
    private String rwiCcPostFilter;
    private String rwiCcSmsType;
    private String rwiCcSmsTemplate;
    private String teId;

}
