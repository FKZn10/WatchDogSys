package com.jpmsoft.jpbpm.pojo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 任务完成情况
 */
@Data
@TableName("Gantt_Tasks_Process")
public class GanttTasksProcess {

    @TableId(type = IdType.INPUT)
    private String gtpId;
    private String taskId;
    private Date gtpDate;
    private Float gtpRate;
    private String gtpRemark;
    private String gtpCreateUid;
    private String gtpCreateUname;
    private Date gtpCreateTime;
    private Integer gtpState;
    private Date gtpStateTime;
    private String gtpImageProgress;
    private String gtpScenePhotos;
    private String gtpLiveVideo;
    private String gtpSignatureConfirm;

}
