package com.jpmsoft.projectProducer.pojo.entity.oa;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author tianhelin
 * @date 2025/6/5
 */
@Data
@TableName("OA_Knowledge_Base_File_Change")
public class OaKnowledgeBaseFileChange {
    @TableId(type = IdType.INPUT)
    private String id;
    private String kbfId;
    private String kbId;
    private String kbfName;
    private String kbfCreateUid;
    private String kbfCreateUname;
    private Date kbfCreateTime;
    private String kbfType;
    private Double kbfSize;
    private Integer kbfState;
    private String kbfCode;
    private String kbfPermission;
    private String kbfTitle;
    private String kbfSummary;
    private String kbfContent;
    private String kbfTag;
    private String kbfCover;
    private String kbfVersionNumber;
    private String kbfFile;
    private String teId;
    private String approveStatus;
    private Date approveTime;
    private Date kbfPublishDate;
    private Integer status;
    private Date statusTime;
    private Date kbfPrintTime;
    private String executionStatus;
    private Boolean groupFiling;
    private Date effectiveDate;
    private Date expiringDate;
    private String effectiveDateType;
}