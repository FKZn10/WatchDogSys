package com.jpmsoft.fileUpload.pojo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 档案备案申请
 */
@Data
@TableName("Archive_Filing_Apply")
public class ArchiveFilingApply {
    @TableId(type = IdType.INPUT)
    private String faId;
    private String pId;
    private String conId;
    private String faType;
    private String faLocation;
    private String faSourceFunds;
    private String faManageUnitId;
    private String faPmId;
    private String faPmName;
    private String faAgentConstructionUnitName;
    private String faConstructionUnitName;
    private String faSurveyUnitName;
    private String faDesignUnitName;
    private String faConsultUnitName;
    private String faSupervisorUnitName;
    private BigDecimal faConstructionContractPrice;
    private BigDecimal faConstructionSettlementPrice;
    private Date faStartDate;
    private Date faEndDate;
    private String faRemark;
    private String faCreateUid;
    private String faCreateUname;
    private Date faCreateTime;
    private Integer faState;
    private Date faStateTime;
}
