package com.jpmsoft.projectProducer.pojo.entity.project;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 项目立项资金来源
 */
@Data
@TableName("Pro_Construction_plan")
public class ProConstructionPlan {

    @TableId(type = IdType.INPUT)
    private String pcpId;
    private String pcpFileName;
    private Integer pcpYear;
    private String pcpCreateUid;
    private String pcpCreateUname;
    private Date pcpCreateTime;
    private Integer pcpState;
    private Date pcpStateTime;
    private String pcpRemarks;
    private String teId;
}
