package com.jpmsoft.projectProducer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

/**
 * @author: 李文涛 aaron
 * @date: 2022/11/11 10:19
 */
@Data
@TableName("Con_Duration_Adjust")
public class ConDurationAdjust extends BaseEntity {

    private String pId;
    private String conId;
    private String daSupervisor;
    private String daType;
    private Float daAdjust;
    private String daRmark;
    private String daReason;
    private String conBId;
    private String teId;

}
