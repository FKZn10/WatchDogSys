package com.jpmsoft.projectProducer.pojo.entity.ganttPlan;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 大事记评论
 *
 * @Author: 李文涛 aaron
 * @date: 2024/9/5 11:16
 */
@Data
@TableName("memoir_comment")
public class MemoirComment {

    @TableId(value = "id", type = IdType.INPUT)
    private String id;
    @TableField(value = "mi_id")
    private String miId;
    @TableField(value = "mc_uid")
    private String mcUid;
    @TableField(value = "mc_uname")
    private String mcUname;
    @TableField(value = "mc_time")
    private Date mcTime;
    @TableField(value = "mc_cpy")
    private String mcCpy;
    @TableField(value = "mc_content")
    private String mcContent;
    @TableField(value = "mc_sort")
    private Integer mcSort;
}
