package com.jpmsoft.projectProducer.pojo.entity.project;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 项目组织机构
 *
 * @since 2024-12-11 17:39:09
 */
@Data
@TableName("pre_project_org_struct")
public class PreProjectOrgStruct implements Serializable {
    private static final long serialVersionUID = 224500148897286655L;
    /**
     * id
     */
    private String id;
    /**
     * 项目id
     */
    private String pId;
    /**
     * 证明文件
     */
    private String proofFile;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建人id
     */
    private String createUid;
    /**
     * 创建人姓名
     */
    private String createUname;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 流程状态
     */
    private Integer state;
    /**
     * 审批时间
     */
    private Date stateTime;



}

