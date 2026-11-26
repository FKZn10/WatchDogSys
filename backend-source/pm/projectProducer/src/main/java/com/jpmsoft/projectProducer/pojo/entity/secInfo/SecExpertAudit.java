package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

/**
 * 评标专家申请
 * @Author: 李文涛 aaron
 * @date: 2024/1/5 9:14
 */
@Data
@TableName("Sec_Expert_Audit")
public class SecExpertAudit extends BaseEntity {

    //名称
    private String seaName;
    private String seaRemark;
    private String teId;
    private String seaCid;
    private Integer year;
}
