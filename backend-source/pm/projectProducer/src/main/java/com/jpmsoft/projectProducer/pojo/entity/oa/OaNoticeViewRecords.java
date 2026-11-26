package com.jpmsoft.projectProducer.pojo.entity.oa;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 收发文管理_查看记录
 * </p>
 *
 * @author zhangl
 * @since 2022-07-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("OA_Notice_View_Records")
public class OaNoticeViewRecords implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "object_id", type = IdType.INPUT)
    private String objectId;


    /**
     * 用户id
     */
    private String userId;


    /**
     * 用户名
     */
    private String userName;


    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;


    /**
     * 修改时间 即 查看时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


    /**
     * 查看某条信息的主键
     */
    private String oaNoticeObjectId;


}
