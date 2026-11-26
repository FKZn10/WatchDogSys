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
 * 资金情况一览
 * </p>
 *
 * @author 刘凯锋
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MpcFunds implements Serializable {

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
     * 项目名
     */
    private String projectName;


    /**
     * 概算金额（万元）
     */
    private String conceptualAmount;


    /**
     * 合同总额（万元）
     */
    private String totalContractAmount;


    /**
     * 支付金额（万元）
     */
    private String paymentAmount;


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
