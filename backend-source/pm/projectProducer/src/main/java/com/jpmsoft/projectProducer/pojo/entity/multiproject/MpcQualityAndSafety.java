package com.jpmsoft.projectProducer.pojo.entity.multiproject;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 质量安全情况 mpc_quality_and_safety
 * </p>
 *
 * @author 刘凯锋
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MpcQualityAndSafety implements Serializable {

    private static final long serialVersionUID = 1L;

    private String objectId;


    /**
     * 年份
     */
    private String particularYear;

    /**
     * type,0-录入，1-业务表获取
     */
    private String type;

    /**
     * 月份
     */
    private String particularMonth;


    /**
     * 安全问题总数
     */
    private String securityTotalNum;


    /**
     * 质量问题总数
     */
    private String qualityTotalNum;


    /**
     * 安全问题整改数
     */
    private String safetyProblemNum;


    /**
     * 质量安全整改数
     */
    private String qualitySafetyNum;


    private String createId;


    private String createName;


    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @TableLogic(value = "0", delval = "1")
    private String deleteStatus;


}
