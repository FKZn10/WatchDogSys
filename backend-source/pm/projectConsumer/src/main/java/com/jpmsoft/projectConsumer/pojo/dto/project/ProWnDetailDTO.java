package com.jpmsoft.projectConsumer.pojo.dto.project;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProWnDetailDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String pwId;
    private String createUid;
    private String createUname;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    private String secAddress;
    private String secContractor;
    private BigDecimal secAmount;
    private String pwPid;
    private String secName;
    private String prwDate;

    private Integer page;
    private Integer size;
}