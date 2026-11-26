package com.jpmsoft.projectProducer.pojo.entity.qualitySafety;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

@Data
@TableName("Qs_PT_Spot_Check")
public class QsPTSpotCheck extends BaseEntity {

    //任务表id
    private String ptId;

    /**
     * 项目ID
     */
    private String pId;
    /**
     * 合同ID
     */
    private String conId;

    //备注
    private String qscRemark;

    private String teId;

}
