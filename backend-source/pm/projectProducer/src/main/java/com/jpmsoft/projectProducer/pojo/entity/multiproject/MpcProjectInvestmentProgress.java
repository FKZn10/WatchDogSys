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
 * 项目投资进度展示
 * </p>
 *
 * @author 刘凯锋
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MpcProjectInvestmentProgress implements Serializable {

    private static final long serialVersionUID = 1L;

    private String objectId;

    /**
     * 年度
     */
    private String yearTime;

    /**
     * type,0-录入，1-业务表获取
     */
    private String type;

    /**
     * 总支付额（万元）
     */
    private String totalPayment;


    /**
     * 当年投资计划额
     */
    private String planAmount;


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
