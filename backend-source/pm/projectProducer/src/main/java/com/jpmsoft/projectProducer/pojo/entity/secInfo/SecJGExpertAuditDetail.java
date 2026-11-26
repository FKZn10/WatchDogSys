package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 评标专家申请专家详情 （建管）
 */
@Data
@TableName("Sec_JG_Expert_Audit_Detail")
public class SecJGExpertAuditDetail {

    @TableId(type = IdType.INPUT)
    private String id;
    private String exId;
    //主表id
    private String seaId;

}
