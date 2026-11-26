package com.jpmsoft.projectProducer.pojo.entity.ganttPlan;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 大事记点赞
 *
 * @Author: 李文涛 aaron
 * @date: 2024/9/5 11:14
 */
@Data
@TableName("memoir_praise")
public class MemoirPraise {

    @TableId(value = "id", type = IdType.INPUT)
    private String id;
    @TableField(value = "mi_id")
    private String miId;
    @TableField(value = "mp_uid")
    private String mpUid;
    @TableField(value = "mp_uname")
    private String mpUname;
    @TableField(value = "mp_cpy")
    private String mpCpy;
    @TableField(value = "mp_time")
    private Date mpTime;
    @TableField(value = "mp_sort")
    private Integer mpSort;
}
