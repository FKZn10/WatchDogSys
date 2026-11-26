package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 集团专家评审主表
 */
@Data
@TableName("Sec_Expert_Audit_Group")
public class SecExpertAuditGroup {
    @TableId(type = IdType.INPUT)
    private String id;
    private String seaName;
    private String seaRemark;
    private Integer state;
    private Date stateTime;
    private Date createTime;
    private String createUid;
    private String createUname;
    private String teId;
    // 这是单位id，集团的表没有使用这个
    private String seaCid;
    private Integer year;
}