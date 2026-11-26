package com.jpmsoft.projectProducer.pojo.entity.qualitySafety;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;


/**
 * 安全责任层级
 */
@Data
@TableName("safe_responsibility_level")
public class SafeResponsibilityLevel {
    /**
     * 主键
     */
    @TableId(type = IdType.INPUT)
    private String id;

    /**
     * 安全责任体系ID
     */
    private String systemId;

    /**
     * 上级层级ID
     */
    private String parentId;

    /**
     * 层级名称
     */
    private String levelName;

    /**
     * 层级说明
     */
    private String levelDescription;

    /**
     * 层级路径
     */
    private String levelPath;

    /**
     * 层级深度
     */
    private Integer levelDepth;

    /**
     * 排序号
     */
    private Integer sortNo;

    /**
     * 状态：0停用，1启用
     */
    private Boolean status;

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