package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("Engineering_Training")
public class EngineeringTraining extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -9023074912254055677L;
    private String id;
    private String etType;
    private Date etTime;
    private String etCompanyName;
    private Integer etPersonNum;
    private String etContent;
    private Integer etIsInvolvedCost;
    private String etForm;
    private String etTeacherLaborCost;
    private BigDecimal etTotal;
    private String etFile;
    private String createUname;
    private String createUid;
    private Date createTime;
    private Integer state;
    private Date stateTime;
}
