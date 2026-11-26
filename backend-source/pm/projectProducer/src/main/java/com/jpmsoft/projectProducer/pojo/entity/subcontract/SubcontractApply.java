package com.jpmsoft.projectProducer.pojo.entity.subcontract;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 分包申请
 */
@Data
@TableName("Subcontract_Apply")
public class SubcontractApply {

    @TableId(type = IdType.INPUT)
    private String saId;
    private String saTitle;
    private String pId;
    private String saGcUnit;
    private String saUnit;
    private String saRemark;
    private String saCreateUid;
    private String saCreateUname;
    private Date saCreateTime;
    private Integer saState;
    private Date saStateTime;
    private String teId;
}
