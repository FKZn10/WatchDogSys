package com.jpmsoft.projectProducer.pojo.dto.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * 月报汇总查询入参
 *
 * @author: 李文涛 aaron
 * @date: 2022/7/8 13:57
 */
@Data
public class ProMonthlyReportDto extends BaseModeRequest implements Serializable {
    private static final long serialVersionUID = -7168557173802809050L;

    private Integer year;
    private Integer month;
    @JsonProperty("pType")
    private String pType;
    @JsonProperty("pName")
    private String pName;
    @JsonProperty("pCompetentDepartment")
    private String pCompetentDepartment;

    @JsonProperty("pId")
    private String pId;
}
