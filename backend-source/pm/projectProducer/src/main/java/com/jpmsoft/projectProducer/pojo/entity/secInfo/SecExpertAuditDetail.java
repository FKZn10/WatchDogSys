package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 评标专家申请专家详情
 * @Author: 李文涛 aaron
 * @date: 2024/1/5 9:15
 */
@Data
@TableName("Sec_Expert_Audit_Detail")
public class SecExpertAuditDetail {

    @TableId(type = IdType.INPUT)
    private String id;
    private String exId;
    private String seaId;
    private String keyExpert;
    private String exChangeStatus;
}
