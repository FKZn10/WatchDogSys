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
 * 变更签证情况
 * </p>
 *
 * @author 刘凯锋
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MpcChangeVisaSituation implements Serializable {

    private static final long serialVersionUID = 1L;

    private String objectId;


    /**
     * 年份
     */
    private String particularYear;


    /**
     * 月份
     */
    private String particularMonth;

    /**
     * type,0-录入，1-业务表获取
     */
    private String type;

    /**
     * 类型名称
     */
    private String typeName;


    /**
     * 变更金额
     */
    private String changeAmount;


    /**
     * 变更数量
     */
    private String changeNum;


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
