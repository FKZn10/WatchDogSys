package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 文档模板扩展
 *
 * @author tianhelin
 * @date 2026/01/17
 */
@Data
@TableName("SYS_FileTemplate_Dtl")
public class SysFileTemplateDtl {
    @TableId(type = IdType.INPUT)
    private String id;
    @TableField("t_id")
    private String tId;
    @TableField("td_FlowPage")
    private String tdFlowPage;
    @TableField("td_InfoPage")
    private String tdInfoPage;
    @TableField("td_UpdatePage")
    private String tdUpdatePage;
    @TableField("td_App_Flowpage")
    private String tdAppFlowpage;
    @TableField("td_App_Infopage")
    private String tdAppInfopage;
    @TableField("td_App_Updatepage")
    private String tdAppUpdatepage;
    private String tdCode;
    private String dId;
    private String pId;
    private Integer status;
    private Date createTime;
    private Date statusTime;
    private String formId;
    private String listSettings;
    private String otherSettings;
}