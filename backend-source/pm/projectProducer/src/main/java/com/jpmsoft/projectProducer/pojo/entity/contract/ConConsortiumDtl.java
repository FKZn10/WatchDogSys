package com.jpmsoft.projectProducer.pojo.entity.contract;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("Con_Consortium_Dtl")
public class ConConsortiumDtl {
    @TableId(type = IdType.INPUT)
    private String id;
    private String conId;
    private String ccdBusiness;
    private String ccdPlan;
    private String cId;
    private String ccdAmt;
    private String ccdRemark;

    @TableField("ccd_CName")
    private String ccdCName;

    private String ccdProjectLeader;

    private String ccdProjectTelephone;

}
