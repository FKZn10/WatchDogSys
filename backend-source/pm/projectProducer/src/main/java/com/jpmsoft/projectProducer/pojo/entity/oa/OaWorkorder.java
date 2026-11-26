package com.jpmsoft.projectProducer.pojo.entity.oa;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 工单
 */
@Data
@TableName("OA_WorkOrder")
public class OaWorkorder {

    @TableId(type = IdType.INPUT)
    private String woId;
    private String woCode;
    private String woTitle;
    private String pId;
    private String conId;
    private String dId;
    private String woPhoneNumber;
    private String woContent;
    private String woType;
    private String woCreateUid;
    private String woCreateUname;
    private Date woCreateTime;
    private Integer woState;
    private Date woStateTime;
    private String woPriority;
    private Date woExpectedTime;
    private Date woActualTime;
    private Date woReplyTime;
    private String woReplyUid;
    private String woReplyUname;
    private String woReplyReason;
    private String woReplySolution;
    private String woReplyContent;
    private String woResolvedState;
    private String teId;
}
