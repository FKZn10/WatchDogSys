package com.jpmsoft.projectProducer.pojo.entity.conSupNotice;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 监理通知单
 */
@Data
@TableName("Con_Sup_Notice")
public class ConSupNotice {

    @TableId(value = "Sn_Id", type = IdType.INPUT)
    private String SnId;

    /**
     * 项目ID
     */
    @TableField("P_Id")
    private String pId;
    /**
     * 项目名
     */
    @TableField("P_Name")
    private String pname;
    /**
     * 合同ID
     */
    @TableField("Con_Id")
    private String conId;
    /**
     * 合同名
     */
    @TableField("Con_Name")
    private String conName;

    /**
     * 编号
     */
    @TableField("Sn_Code")
    private String snCode;

    /**
     * 通知单编号
     */
    @TableField("Sn_No")
    private String snNo;
    /**
     * 发文单位ID
     */
    @TableField("Sn_Issuing_Unit_Id")
    private String snIssuingUnitId;
    /**
     * 发文单位
     */
    @TableField("Sn_Issuing_Unit_Name")
    private String snIssuingUnitName;
    /**
     * 收文单位ID
     */
    @TableField("Sn_Receiving_Unit_Id")
    private String snReceivingUnitId;

    /**
     * 收文单位名称
     */
    @TableField("Sn_Receiving_Unit_Name")
    private String snReceivingUnitName;
    /**
     * 发文日期
     */
    @TableField("Sn_Issue_Date")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date snIssueDate;
    /**
     * 是否需要收文单位回复
     */
    @TableField("Sn_Need_Reply")
    private String snNeedReply;
    /**
     * 限定回复日期
     */
    @TableField("Sn_Reply")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date snReply;
    /**
     * 文件标题
     */
    @TableField("Sn_Reply_Deadline")
    private String snReplyDeadline;
    /**
     * 文件内容
     */
    @TableField("Sn_Content")
    private String snContent;
    /**
     * 是否已回复
     */
    @TableField("Sn_Reply_State")
    private String snReplyState;

    /**
     * 创建人ID
     */
    @TableField("Sn_Create_Uid")
    private String snCreateUid;
    /**
     * 创建人姓名
     */
    @TableField("Sn_Create_Uname")
    private String snCreateUname;

    /**
     * 创建时间
     */
    @TableField(value = "Sn_Create_Time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date snCreateTime;

    /**
     * 状态
     */
    @TableField("Sn_State")
    private String snState;

    /**
     * 审批时间
     */
    @TableField("Sn_State_Time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date snStateTime;

    @TableField("file_relation_id")
    private String fileRelationId;

    // @TableField("Delete_State")
//    @TableLogic(value = "0", delval = "1")
//    private String deleteState;
    @TableField(exist = false)
    private String rteFileIds;


    @TableField(exist = false)
    private ConSupNoticeReply noticeReplyPojo;

    private String teId;

    /**
     * 关联的监理报表id（oa_work_report.Wr_Id）
     */
    @TableField("sup_Id")
    private String supId;

    /**
     * 问题数量，取监理报表子表 oa_work_report_detail 的 wrd_number 合计
     */
    @TableField("sup_Num_Question")
    private Integer supNumQuestion;

    /**
     * 通知单类型（监理日报、周报、月报等）
     */
    @TableField("Sup_Type")
    private String supType;

    /**
     * 对应周期
     */
    @TableField("sup_Sign_Time")
    private String supSignTime;

    @TableField(exist = false)
    private List<ConSupNoticeDw> conSupNoticeDws;

    @TableField(exist = false)
    @JsonProperty("tCode")
    private String tCode;

    @TableField(exist = false)
    @JsonProperty("tId")
    private String tId;
}
