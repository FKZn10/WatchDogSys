package com.jpmsoft.projectProducer.pojo.entity.qualitySafety;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 安全责任体系
 */
@Data
@TableName("safe_responsibility_system")
public class SafeResponsibilitySystem {
    /**
     * 主键
     */
    @TableId(type = IdType.INPUT)
    private String id;

    /**
     * 体系名称
     */
    private String systemName;

    /**
     * 体系类型：1标准模板，2项目自定义
     */
    private Integer systemType;

    /**
     * 项目id，标准模板不关联项目
     */
    private String pId;

    /**
     * 合同id
     */
    private String conId;

    /**
     * 复制来源体系ID（复制的标准模板来源，完全自定义的没有来源id）
     */
    private String sourceSystemId;

    /**
     * 版本号
     */
    private Integer versionNo;

    /**
     * 状态：0停用，1启用
     */
    private Boolean status;

    /**
     * 说明
     */
    private String remark;

    /**
     * 创建人ID
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
     * 修改人ID
     */
    private String updateUid;

    /**
     * 修改人姓名
     */
    private String updateUname;

    /**
     * 修改时间
     */
    private Date updateTime;
}