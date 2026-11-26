package com.jpmsoft.projectProducer.pojo.entity.integratedWork;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 三联单明细表
 */
@Data
@TableName("Integrated_Work_Order_Dtl")
public class IntegratedWorkOrderDtl {
    @TableId(type = IdType.INPUT)
    private String id;
    private String iwoId;
    private String engineeringLocation;
    private String constructionContent;
    private String zlcontrolNodes;
    private String aqcontrolNodes;
    private String threeSgdwConstructionId;
    private String threeSgdwConstructionName;
    private String threeSgdwConstructionTel;
    private String threeSgdwQualityId;
    private String threeSgdwQualityName;
    private String threeSgdwQualityTel;
    private String threeSgdwSafetyId;
    private String threeSgdwSafetyName;
    private String threeSgdwSafetyTel;
    private String threeJlId;
    private String threeJlName;
    private String threeJlTel;
    private String threeLegalpersonId;
    private String threeLegalpersonName;
    private String threeLegalpersonTel;
    private String threeMajorContent;
    private String iwodType;
    private Date createTime;
    private Date threeDate;

    private String threeResponsibleId;
    private String threeResponsibleName;
    private String threeResponsibleTel;
}