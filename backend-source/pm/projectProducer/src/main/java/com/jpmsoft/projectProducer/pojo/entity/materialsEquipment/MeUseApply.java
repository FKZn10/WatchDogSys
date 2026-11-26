package com.jpmsoft.projectProducer.pojo.entity.materialsEquipment;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 使用报审
 */
@Data
@TableName("Me_Use_Apply")
public class MeUseApply {

    @TableId(type = IdType.INPUT)
    private String uaId;
    private String eaId;
    private String pId;
    private String conId;
    private String uaRemark;
    private String uaCreateUid;
    private String uaCreateUname;
    private Date uaCreateTime;
    private Integer uaState;
    private Date uaStateTime;
    private String teId;
}
