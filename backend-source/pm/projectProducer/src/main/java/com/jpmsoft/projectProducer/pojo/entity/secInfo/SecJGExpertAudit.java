package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

/**
 * 评标专家申请 (监管)
 */
@Data
@TableName("Sec_JG_Expert_Audit")
public class SecJGExpertAudit extends BaseEntity {

    //名称
    private String seaName;
    private String seaRemark;
    private String teId;
    //单位id
    private String seaCid;
    /**
     * 内外部专家入库 0：外部 1、内部
     */
    private Integer seaInsideType;
}
