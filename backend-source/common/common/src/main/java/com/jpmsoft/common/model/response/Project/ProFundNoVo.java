package com.jpmsoft.common.model.response.Project;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目经费号
 *
 * @author: 李文涛 aaron
 * @date: 2022/7/20 14:47
 */
@Data
public class ProFundNoVo implements Serializable {


    private static final long serialVersionUID = -7123673227513444176L;
    private String id;
    @JsonProperty("pId")
    private String pId;
    private String fnCode;
    private BigDecimal fnMoney;
    private String fnRemark;
    private Integer fnSort;
    private Date createTime;
}
