package com.jpmsoft.projectProducer.pojo.entity.materialsEquipment;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 进场验货
 */
@Data
@TableName("Me_Enter_Inspection")
public class MeEnterInspection {
    @TableId(type = IdType.INPUT)
    private String eiId;
    private String eaId;
    private String pId;
    private String conId;
    private String eiSupervisorId;
    private String eiDesignId;
    private String eiContractorId;
    private String eiSupplierId;
    private String eiAcceptPic;
    private Date eiAcceptTime;
    private String eiAcceptorId;
    private String eiAcceptorName;
    private String eiCreateUid;
    private String eiCreateUname;
    private Date eiCreateTime;
    private Integer eiState;
    private Date eiStateTime;
    private String eiCode;
    private String teId;
}
