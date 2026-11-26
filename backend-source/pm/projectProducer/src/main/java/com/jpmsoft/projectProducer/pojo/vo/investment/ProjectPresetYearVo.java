package com.jpmsoft.projectProducer.pojo.vo.investment;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProjectPresetYearVo {

    private String id;

    private String createUid;

    private String createUname;

    private Date createTime;

    private Integer state;

    private Date stateTime;

    private String pId;

    private Integer pyEclareYear;



    private BigDecimal pyInvestmentAmount;

    private Integer page;

    private Integer size;
}
