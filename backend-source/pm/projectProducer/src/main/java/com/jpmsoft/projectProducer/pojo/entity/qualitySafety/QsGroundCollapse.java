package com.jpmsoft.projectProducer.pojo.entity.qualitySafety;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 地面坍塌情况
 */
@Data
@TableName("Qs_Ground_Collapse")
public class QsGroundCollapse {

    @TableId(type = IdType.INPUT)
    private String gcId;

    /**
     * 项目ID
     */
    private String pId;

    /**
     * 有无地面坍塌
     */
    private String gcHas;

    /**
     * 发现和处理地面坍塌情况
     */
    private String gcExplain;

    /**
     * 发现时间
     */
    private Date gcTime;

    /**
     * 处理时间
     */
    private Date gcHandleTime;

    /**
     * 备注
     */
    private String gcRemark;
    private String gcCreateUid;
    private String gcCreateUname;
    private Date gcCreateTime;

    /**
     * 审批状态
     */
    private Integer gcState;

    /**
     * 审批时间
     */
    private Date gcStateTime;
    private String teId;
}
