package com.jpmsoft.projectProducer.pojo.function;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 功能时间配置
 * </p>
 *
 * @author zhangl
 * @since 2022-08-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("function_time_config")
public class FunctionTimeConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "object_id", type = IdType.INPUT)
    private String objectId;


    /**
     * 项目id
     */
    private String projectId;


    /**
     * 项目名称
     */
    private String projectName;


    /**
     * 功能编码
     */
    private String functionCode;


    /**
     * 功能名称
     */
    private String functionName;


    /**
     * 备注
     */
    private String remarks;


    private String createId;


    private String createName;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startTime;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endTime;

    @TableLogic(value = "0", delval = "1")
    private String deleteStatus;

    /**
     * 是否排除法定节假日，0-否，1-是
     */
    private String isWork;


}
