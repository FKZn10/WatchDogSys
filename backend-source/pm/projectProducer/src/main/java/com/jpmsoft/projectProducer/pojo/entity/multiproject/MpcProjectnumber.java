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
 * 多项目驾驶舱-项目数量
 * </p>
 *
 * @author 刘凯锋
 * @since 2022-08-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MpcProjectnumber implements Serializable {

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
     * 在建项目数
     */
    private String buildingNum;


    /**
     * 已建项目数
     */
    private String alreadyBuiltNum;


    /**
     * 拟建（前期）项目数
     */
    private String proposedNum;


    /**
     * 规划项目数
     */
    private String planNum;


    private String createId;


    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @TableLogic(value = "0", delval = "1")
    private String deleteStatus;


    private String createName;


}
