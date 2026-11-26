package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 集团专家评审子表
 */
@Data
@TableName("Sec_Expert_Audit_Group_Detail")
public class SecExpertAuditGroupDetail {
    @TableId(type = IdType.INPUT)
    private String id;
    private String exId;
    private String seaId;
    private String keyExpert;
    private String exChangeStatus;
}