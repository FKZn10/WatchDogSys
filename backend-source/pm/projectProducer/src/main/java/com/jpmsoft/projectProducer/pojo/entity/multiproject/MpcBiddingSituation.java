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
 * 招投标情况一览
 * </p>
 *
 * @author 刘凯锋
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MpcBiddingSituation implements Serializable {

    private static final long serialVersionUID = 1L;

    private String objectId;


    /**
     * 类型
     */
    private String type;

    /**
     * 来源类型0-手动录入，1-定时任务拉取业务表数据
     */
    private String sourceType;


    /**
     * 计划金额
     */
    private String planAmount;


    /**
     * 计划个数
     */
    private String planNum;


    /**
     * 实际金额
     */
    private String actualAmout;


    /**
     * 实际个数
     */
    private String actualNumber;


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
