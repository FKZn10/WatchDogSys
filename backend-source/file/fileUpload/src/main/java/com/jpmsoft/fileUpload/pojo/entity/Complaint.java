package com.jpmsoft.fileUpload.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 举报表
 */
@Data
@TableName("tb_complaint")
public class Complaint {

    @TableId(type = IdType.INPUT)
    private String id;
    /**
     * 投诉时间，来自模板“投诉时间”列。
     */
    private Date complaintTime;
    /**
     * 化解回复时间，来自模板“化解回复时间”列。
     */
    private Date replyTime;
    /**
     * 项目ID，由模板项目名称匹配 Pre_Project.P_Name 后写入 P_Id。
     */
    private String preId;
    /**
     * 企业ID，由模板企业名称匹配 sys_company.c_name 后写入 c_id。
     */
    private String unitId;
    /**
     * 匹配确认的合同ID；不能唯一确认时为空。
     */
    private String contractId;
    /**
     * 用户导入时填写的原始项目名称。
     */
    private String originalProjectName;
    /**
     * 用户导入时填写的原始单位名称。
     */
    private String originalUnitName;
    /**
     * 匹配状态：00-完全成功，01-项目成功但单位或合同不明确，02-单位成功但项目不明确，03-项目单位均失败。
     */
    private String matchStatus;
    /**
     * 是否需要人工确认。
     */
    private Boolean needManualConfirm;
    /**
     * 兼容旧缺失类型字段：01-项目问题，02-单位问题，0102-项目和单位问题。
     */
    private String missingType;
    /**
     * 兼容旧缺失信息字段，记录本次匹配的人工确认原因。
     */
    private String missingInfo;
    /**
     * 举报人姓名；匹配到 sys_user 时使用系统姓名，匹配不到保留模板输入。
     */
    private String reporterName;
    /**
     * 举报人ID；按 sys_user.u_name 匹配成功时写入 u_id，未匹配时为空。
     */
    private String reporterId;
    /**
     * 举报电话，作为重复数据判断条件之一。
     */
    private String phoneNumber;
    /**
     * 案件来源，按字典 JPMsoft_AJLY 模糊匹配后保存 d_code。
     */
    private String caseSource;
    /**
     * 电话投诉内容，作为重复数据判断条件之一。
     */
    private String complaintContent;
    private String investigationResult;
    private Integer wageCount;
    private BigDecimal wageAmount;
    /**
     * 国平台投诉是否入黑名单，保存是否字典值：是 01，否 02。
     */
    private String blacklistStatus;
    private String identityNumber;
    /**
     * 创建人ID，取当前 token 用户。
     */
    private String createUid;
    /**
     * 创建人名称，取当前 token 用户。
     */
    private String createUname;
    /**
     * 导入写入时间；重复数据更新时也刷新为本次导入时间。
     */
    private Date createTime;
    /**
     * 审批状态，导入固定写入 7。
     */
    private Integer state;
    /**
     * 审批状态时间，导入时取当前时间。
     */
    private Date stateTime;
}
