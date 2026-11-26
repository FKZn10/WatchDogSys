package com.jpmsoft.projectProducer.pojo.entity.cockpit;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 项目全景及工程概况
 */
@Data
@TableName("Project_Panorama_Overview")
public class ProjectPanoramaOverview {
    @TableId(type = IdType.INPUT)
    private String ppoId;
    private String pId;
    private Date ppoData;
    private String ppoFileType;
    private String ppoFile;
    private Integer ppoState;
    private String ppoRemarks;
    private String ppoType;
    private String ppoCreateUid;
    private String ppoCreateUname;
    private Date ppoCreateTime;
    private String teId;
    private String contractId;
}
