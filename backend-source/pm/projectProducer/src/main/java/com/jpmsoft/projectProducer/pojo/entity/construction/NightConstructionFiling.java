package com.jpmsoft.projectProducer.pojo.entity.construction;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 夜间施工备案
 *
 * @Author: 李文涛 aaron
 * @date: 2023/10/24 10:48
 */
@Data
@TableName("Night_Construction_Filing")
public class NightConstructionFiling extends BaseEntity {

    @JsonProperty("pId")
    private String pId;
    private String conId;
    private String ncfLeader;
    private String ncfLeaderPhone;
    private String ncfWorkContent;
    private Integer ncfWorkNumber;
    private String ncfAreaSection;
    private Date ncfStartTime;
    private Date ncfEndTime;
    private String ncfWorker;
    private String ncfWorkerPhone;
    private String ncfSide;
    private String ncfSidePhone;
    private String ncfRemark;
}
