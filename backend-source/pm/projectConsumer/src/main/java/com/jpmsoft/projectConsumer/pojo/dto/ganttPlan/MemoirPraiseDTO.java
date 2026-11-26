package com.jpmsoft.projectConsumer.pojo.dto.ganttPlan;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 大事记点赞
 *
 * @Author: 李文涛 aaron
 * @date: 2024/9/5 11:14
 */
@Data
public class MemoirPraiseDTO implements Serializable {

    private static final long serialVersionUID = -5281840475629394582L;

    private String id;
    private String miId;
    private String mpUid;
    private String mpUname;
    private String mpCpy;
    private Date mpTime;
    private Integer mpSort;
    private String mpCpyName;
}
