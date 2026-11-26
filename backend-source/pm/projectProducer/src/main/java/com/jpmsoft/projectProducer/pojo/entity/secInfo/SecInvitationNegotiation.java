package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 邀标控制价
 */
@Data
@TableName("Sec_Invitation_Negotiation")
public class SecInvitationNegotiation {
    @TableId(type = IdType.INPUT)
    private String id;
    private String createUid;
    private String createUname;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    private String pId;
    private String secId;

    /**
     * 评标方法
     */
    private String inFunctionText;
    private String inRemark;

    /**
     * 关联图纸id
     */
    private String inAssociatedDrawings;

    /**
     * 邀请发布日期
     */
    private Date inInvitationTime;

    /**
     * 计划开标时间
     */
    private Date inPlanOpeningTime;

    /**
     * 计划开标地点
     */
    private String inPlanBidOpenLocation;
}