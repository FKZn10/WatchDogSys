package com.jpmsoft.projectProducer.pojo.entity.archive;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 项目档案归档信息
 */
@Data
@TableName("Archive")
public class Archive {

    @TableId(type = IdType.INPUT)
    private String apId;
    private String adId;
    private String pId;
    private String apPersonUid;
    private String apPersonUname;
    private Integer apState;
    private String apTpyeCode;
    private String apReceiptNo;
    private Date apReceiptTime;
    private String apDocumentNo;
    private String apAuthor;
    private String apTitle;
    private Date apWrittenDate;
    private Integer apPageNumber;
    private Integer apCopies;
    private Integer apA4PaperNum;
    private String apPosition;
    private String apKeyword;
    private String apRemark;
    private String apDistribute;
    private String apEntityDirection;
    private String apDescCreateUid;
    private String apDescCreateUname;
    private Date apDescCreateTime;
    private String apEntryMethod;
    private String apSecrecyLevel;
    private Integer apRetentionPeriod;
    private String apTransferRemark;
    private String apTransferUid;
    private String apTransferUname;
    private String apReceiverUid;
    private String apReceiverUname;
    private Integer apTransferState;
    private Date apTransferTime;
    private String apTransferCreateUid;
    private String apTransferCreateUname;
    private Date apTransferCreateTime;
    private Date apApprovalDate;
    private String apIssueCompany;
    private String teId;
}
