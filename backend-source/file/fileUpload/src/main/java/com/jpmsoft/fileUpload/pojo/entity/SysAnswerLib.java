package com.jpmsoft.fileUpload.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 问答知识库
 *
 * @Author: 李文涛 aaron
 * @date: 2023/12/25 9:54
 */
@Data
@TableName("Sys_Answer_Lib")
public class SysAnswerLib {

    @TableId(type = IdType.INPUT)
    private String id;
    private String alTitle;
    private String alContent;
    private String alType;
    private String alWebUrl;
    private String alAppUrl;
    private boolean alAsked;
    private String  alCode;
    private String  alKeys;
    private Integer alCount;
    private String createUname;
    private String createUid;
    private Date createTime;

    private String tCode;
    private String powerCode;
}
