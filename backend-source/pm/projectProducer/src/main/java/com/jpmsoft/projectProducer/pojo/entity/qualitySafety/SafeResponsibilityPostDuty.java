package com.jpmsoft.projectProducer.pojo.entity.qualitySafety;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 安全责任岗位职责明细
 */
@Data
@TableName("safe_responsibility_post_duty")
public class SafeResponsibilityPostDuty {
    /**
     * 主键
     */
    @TableId(type = IdType.INPUT)
    private String id;

    /**
     * 岗位责任ID
     */
    private String postId;

    /**
     * 岗位职责内容
     */
    private String dutyContent;

    /**
     * 排序号
     */
    private Integer sortNo;

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