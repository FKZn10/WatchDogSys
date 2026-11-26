package com.jpmsoft.fileUpload.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * 打印模板
 */
@Data
@TableName("SYS_FileTemplate_Print")
public class SysFileTemplatePrint {

    @TableId(type = IdType.INPUT)
    private String tpId;
    @JsonProperty("tId")
    private String tId;
    private Integer tpOrder;
    private String tpContent;
    private String tpCreateUid;
    private String tpCreateUname;
    private Date tpCreateTime;
    private String tpApiUrl;
    private String tpName;
    private String tpRemark;
    private Integer tpState;
    private String tpType;
    private String teId;
}
