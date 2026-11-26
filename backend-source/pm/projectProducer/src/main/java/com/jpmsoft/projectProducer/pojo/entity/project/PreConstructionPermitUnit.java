package com.jpmsoft.projectProducer.pojo.entity.project;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 参建单位明细
 */
@Data
@TableName("Pre_Construction_Permit_Unit")
public class PreConstructionPermitUnit {
    @TableId(type = IdType.INPUT)
    private String cpuId;
    private String cpId;
    private String cpuCid;
    private String cpuPmUid;
    private String cpuPmUname;
    private String cpuTel;
    private Date cpuCreateTime;
    private Integer cpuOrder;
}
