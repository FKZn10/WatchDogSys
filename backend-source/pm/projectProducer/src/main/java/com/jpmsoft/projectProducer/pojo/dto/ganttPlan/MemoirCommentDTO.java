package com.jpmsoft.projectProducer.pojo.dto.ganttPlan;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 大事记评论
 *
 * @Author: 李文涛 aaron
 * @date: 2024/9/5 11:16
 */
@Data
public class MemoirCommentDTO implements Serializable {

    private static final long serialVersionUID = -8590328826159975781L;
    private String id;
    private String miId;
    private String mcUid;
    private String mcUname;
    private Date mcTime;
    private String mcCpy;
    private String mcContent;
    private Integer mcSort;
    private String mcCpyName;
}
