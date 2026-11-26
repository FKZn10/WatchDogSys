package com.jpmsoft.fileUpload.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 风险点防控措施
 *
 * @Author: 李文涛 aaron
 * @date: 2023/3/29 11:29
 */
@Data
@TableName("Risk_Prevention_Measure")
public class RiskPreventionMeasure {
    @TableId(type = IdType.INPUT)
    private String id;
    private String createUid;
    private String createUname;
    private Date createTime;
    private String rpmParentId;
    private String rpmType;
    private String rpmLevel;
    private String rpmPoints;
    private String rpmMeasure;
    private String rpmInvolved;
    private String rpmDep;
    private String rpmContent;
    private Integer rpmSort;

    private String rpmIcon;
    private String rpmIconColor;
    private String rpmStatus;

}
