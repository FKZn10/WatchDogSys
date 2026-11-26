package com.jpmsoft.projectProducer.pojo.entity.oa;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 督办任务
 */
@Data
@TableName("OA_Supervision_Task")
public class OaSupervisionTask {
    @TableId(type = IdType.INPUT)
    private String stId;
    private String stDId;
    private String pId;
    private String stCreateUid;
    private String stCreateUname;
    private Date stCreateTime;
    private Integer stState;
    private Date stStateTime;
    private String stCode;
    private Date stTerm;
    private String stTitle;
    private String stType;
    private String stHandleType;
    private String stContent;
    private String stRemark;
    private String stPerson;
    private String stPersonName;
    private String teId;
}
