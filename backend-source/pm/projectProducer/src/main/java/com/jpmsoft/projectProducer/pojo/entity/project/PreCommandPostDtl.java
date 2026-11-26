package com.jpmsoft.projectProducer.pojo.entity.project;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 指挥部架构职务明细
 */
@Data
@TableName("Pre_Command_Post_Dtl")
public class PreCommandPostDtl {

    @TableId(type = IdType.INPUT)
    private String comdId;
    private String comId;
    private String comdPostCode;
    private String comdCid;
    private String comdUid;
    private String comdUname;
    private String comdTel;
    private Date comdCreateTime;
    private Integer comdOrder;
    private String comdCname;
}
