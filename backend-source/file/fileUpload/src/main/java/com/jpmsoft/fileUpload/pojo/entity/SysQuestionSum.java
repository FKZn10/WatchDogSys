package com.jpmsoft.fileUpload.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 用户问题记录
 * @Author: 李文涛 aaron
 * @date: 2023/12/25 13:36
 */
@Data
@TableName("Sys_Question_Sum")
public class SysQuestionSum {

    @TableId(type = IdType.INPUT)
    private String id;
    private String asQuestion;
    private Boolean asAnswer;
    private String createUname;
    private String createUid;
    private Date createTime;
}
