package com.jpmsoft.projectProducer.pojo.entity.investment;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("t_information_filling")
public class TInformationFilling {

    private String id;

    private String lon;

    private String lat;

    private String classification;

    private String itemclassification;

    @TableField("typeofConstruction")
    private String typeofConstruction;

    private BigDecimal scalekey;

    private String unit;

    private String constructioncycle;

    private String constructioncontent;

    private BigDecimal invest;

    private BigDecimal roughestimate;

    private BigDecimal reckon;

    private BigDecimal budestimate;

    private BigDecimal budget;

    private String projectstart;

    private String startwork;

    private String becompleted;

    @TableField("BIMskill")
    private String BIMskill;

    private String wisdomsite;

    private String projectmanagement;

    private String newtechnolony;

    private String thenewmaterial;

    private String thenewprocess;

    private String energysaving;

    private String inrhythmic;

    private String watersaving;

    private String savingmaterial;

    private String fabricatedconstruction;

    private String digitalconstruction;

    private String greenbuilding;

    private String focusontheconstruction;

    private String generalprojectcontracting;

    private String engineeringconsulting;

    private String architectincharge;

    private String name;

    private String reportingYear;

    private String reportingMonth;

    private BigDecimal plannedforlastquarter;

    private BigDecimal planforthisquarter;

    private BigDecimal completedlastquarter;

    private BigDecimal completethequarter;

    private BigDecimal paylastquarter;

    private BigDecimal payforthequarter;

    private String imageprogress;

    private BigDecimal projectoutput;

    private BigDecimal theconstructionareaof;

    private String conunit;

    private BigDecimal newstartarea;

    private String newsunit;

    private Integer numberofpersonnel;

    private BigDecimal completionofthevalue;

    private BigDecimal completionarea;

    private String comunit;

    @TableField("completedAmount")
    private String completedAmount;

    private BigDecimal newMonthContractAmount;

    private BigDecimal totalcontractamount;

    private BigDecimal carryforwardthecontractamount;

    private BigDecimal newcontractamount;

    @TableField("firstQuarterTarget")
    private BigDecimal firstQuarterTarget;

    @TableField("secondQuarterTarget")
    private BigDecimal secondQuarterTarget;

    @TableField("thirdQuarterTarget")
    private BigDecimal thirdQuarterTarget;

    @TableField("fourthQuarterTarget")
    private BigDecimal fourthQuarterTarget;

    private String nationallevel;

    private String provinciallevel;

    private String citylevel;

    private String otherlevel;

    private String note;

    private String briefanalysisreport;

    private String oid;

    private String did;

    private String rang;

    private Date creattime;

    @TableField("sourceType")
    private Integer sourceType;

    private String pId;

    private String createUid;

    private String createUname;

    private Date createTime;

    private String state;

    private Date stateTime;

    @TableField("uuid")
    private String uuid;
}
