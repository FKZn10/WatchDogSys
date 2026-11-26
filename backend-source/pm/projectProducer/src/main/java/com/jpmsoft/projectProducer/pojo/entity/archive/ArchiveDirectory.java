package com.jpmsoft.projectProducer.pojo.entity.archive;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 档案目录
 */
@Data
@TableName("Archive_Directory")
public class ArchiveDirectory {

    @TableId(type = IdType.INPUT)
    private String adId;
    private String adName;
    private String adCode;
    private String adPersonUid;
    private String adPersonUname;
    private Integer adOrder;
    private String adNo;
    private String adRpBuild;
    private String adRpConstruction;
    private String adRpDesign;
    private String adRpSupervisor;
    private String adRpCity;
    private String adCreateUid;
    private String adCreateUname;
    private Date adCreateTime;
    private String adVersion;
    private Integer adState;
    private String adParentId;
    private String adRemark;
    private String adLink;
    private String adType;
    private String teId;
}
