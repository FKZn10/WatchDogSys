package com.jpmsoft.projectProducer.pojo.entity.project;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("Pre_Project_Solution_Details")
public class PreProjectSolutionDetails implements Serializable {
    private static final long serialVersionUID = -7841600050347027050L;
    private String id;
    private String solutionId;
    private String infoType;
    private String categoryFirst;
    private String categorySecond;
    private String name;
    private String description;
    private String applications;
    private Date startDate;
    private Date endDate;
    private BigDecimal areaUsed;
    private BigDecimal areaAvailable;
    private BigDecimal score;
    private BigDecimal selfScore;
    private BigDecimal scoreReal;
    private String appStage;
    private String responsibleParty;
    private Integer delFlag;
    private Integer status;
}
