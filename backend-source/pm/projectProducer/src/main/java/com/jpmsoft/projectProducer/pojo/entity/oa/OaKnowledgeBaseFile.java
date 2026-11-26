package com.jpmsoft.projectProducer.pojo.entity.oa;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 知识库文件
 */
@Data
@TableName("OA_Knowledge_Base_File")
public class OaKnowledgeBaseFile {

    @TableId(type = IdType.INPUT)
    private String kbfId;
    private String kbId;
    private String kbfName;
    private String kbfCreateUid;
    private String kbfCreateUname;
    private Date kbfCreateTime;
    private String kbfType;
    private Float kbfSize;
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
    private Date kbfPublishDate;

    private String executionStatus;
    private Boolean groupFiling;
    private Date effectiveDate;
    private Date expiringDate;
    private String effectiveDateType;

    // 发文单位
    private String partyAsUnitName;
}
