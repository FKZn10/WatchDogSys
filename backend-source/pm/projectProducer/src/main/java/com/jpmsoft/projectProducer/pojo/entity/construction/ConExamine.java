package com.jpmsoft.projectProducer.pojo.entity.construction;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 施工（准备）审核
 */
@Data
@TableName("Con_Examine")
public class ConExamine {

    @TableId(type = IdType.INPUT)
    private String exId;
    private String pId;
    private String exContractorId;
    private String exSupervisorId;
    private String exRemark;
    private String exCreateUid;
    private String exCreateUname;
    private Date exCreateTime;
    private Integer exState;
    private Date exStateTime;
    private String exTitle;
    private String teId;
}
