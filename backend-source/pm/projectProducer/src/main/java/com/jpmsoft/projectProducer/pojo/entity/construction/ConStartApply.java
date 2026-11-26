package com.jpmsoft.projectProducer.pojo.entity.construction;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 开工报审
 */
@Data
@TableName("Con_Start_Apply")
public class ConStartApply {

    @TableId(type = IdType.INPUT)
    private String saId;
    private String pId;
    private String saContractorId;
    private String saSupervisorId;
    private String saRemark;
    private String saCreateUid;
    private String saCreateUname;
    private Date saCreateTime;
    private Integer saState;
    private Date saStateTime;
    private String saTitle;
    private String teId;
    private Date saDate;
}
