package com.jpmsoft.projectProducer.pojo.entity.conSupNotice;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 监理通知单回复
 */
@Data
@TableName("Con_Sup_Notice_Reply")
public class ConSupNoticeReply {

    @TableId(value = "Snr_Id", type = IdType.INPUT)
    private String snrId;

    @TableField("Sn_Id")
    private String snId;
    /**
     * 项目ID
     */
    @TableField("P_Id")
    private String pid;
    /**
     * 合同ID
     */
    @TableField("Con_Id")
    private String conId;

    /**
     * 回复编号
     */
    @TableField("Snr_Code")
    private String snrCode;
    /**
     * 回复标题
     */
    @TableField("Snr_Title")
    private String snrTitle;
    /**
     * 回复内容
     */
    @TableField("Snr_Content")
    private String snrContent;
    /**
     * 创建人ID
     */
    @TableField("Snr_Create_Uid")
    private String snrCreateUid;
    /**
     * 创建人姓名
     */
    @TableField("Snr_Create_Uname")
    private String snrCreateUname;

    /**
     * 创建时间
     */
    @TableField(value = "Snr_Create_Time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date snrCreateTime;
    /**
     * 审批状态
     */
    @TableField("Snr_State")
    private String snrState;
    /**
     * 审批时间
     */
    @TableField("Snr_State_Time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date snrStateTime;

    @TableField("file_relation_id")
    private String fileRelationId;

    // @TableField("Delete_State")
//    @TableLogic(value = "0", delval = "1")
//    private String deleteState;
    @TableField(exist = false)
    private String rteFileIds;
    private String teId;

    /**
     * 第几次回复
     */
    @TableField("sn_Whatnumber")
    private BigDecimal snWhatnumber;

    /**
     * 区分是否是项目协同管理平台下的监理通知回复单
     */
    private String isProjectCooperation;

    /**
     * 报送单位
     */
    private String snSendingUnitName;

    /**
     * 报送单位ID
     */
    private String snSendingUnitId;

    /**
     * 抄送单位
     */
    private String snCcUnitName;

    /**
     * 抄送单位ID
     */
    private String snCcUnitId;

    /**
     * 相关材料共几页
     */
    private Integer pageNum;

    /**
     * 类型
     */
    private String snrType;

    /**
     * 是否下发
     */
    private String issued;
}
