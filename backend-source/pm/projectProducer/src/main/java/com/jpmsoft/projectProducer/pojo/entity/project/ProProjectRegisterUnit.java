package com.jpmsoft.projectProducer.pojo.entity.project;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 项目登记参建单位明细，不继承包含审批字段的BaseEntity。
 */
@Data
@TableName("Pro_Project_Register_Unit")
public class ProProjectRegisterUnit {

    /**
     * 明细主键。
     */
    @TableId(type = IdType.INPUT)
    private String id;
    /**
     * 项目登记主表ID。
     */
    private String registerId;
    /**
     * 单位类型字典编码。
     */
    private String unitType;

    /**
     * 单位名称。
     */
    private String unitName;

    /**
     * 联系人。
     */
    private String contactName;

    /**
     * 联系电话。
     */
    private String contactPhone;

    /**
     * 排序号，不传时按数组顺序从1开始。
     */
    private Integer sortNo;

    /**
     * 创建人ID。
     */
    private String createUid;
    /**
     * 创建人姓名。
     */
    private String createUname;
    /**
     * 创建时间。
     */
    private Date createTime;
}

