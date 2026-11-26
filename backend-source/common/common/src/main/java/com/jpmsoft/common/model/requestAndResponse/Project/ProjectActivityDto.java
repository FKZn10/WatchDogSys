package com.jpmsoft.common.model.requestAndResponse.Project;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author jjz
 */
@Data
public class ProjectActivityDto {

    // 项目个数
    private int projectCount;

    // 签订合同额
    private BigDecimal signContractAmount = BigDecimal.ZERO;

    // 上年结转合同额
    private BigDecimal carryForwardContractAmount = BigDecimal.ZERO;

    // 本年新签合同额
    private BigDecimal newSignContractAmount = BigDecimal.ZERO;

    // 项目总产值
    private BigDecimal totalOutputValue = BigDecimal.ZERO;

    // 其中：党政机关/事业单位等相关设施工程产值
    private BigDecimal govFacilityValue = BigDecimal.ZERO;

    // 其中：科教文体卫民等社会事业工程产值
    private BigDecimal socialCauseValue = BigDecimal.ZERO;

    // 其中：公共基础设施产值
    private BigDecimal infrastructureValue = BigDecimal.ZERO;

    // 其他产值
    private Double otherValue = 0d;

    // 竣工产值
    private BigDecimal finishValue = BigDecimal.ZERO;

    // 施工面积
    private BigDecimal constructionArea = BigDecimal.ZERO;

    // 其中：新开工面积
    private BigDecimal newlyStartArea = BigDecimal.ZERO;

    // 从事集中建设活动的平均人数
    private Double avgStaffCount;

    // 省（自治区、直辖市）名称
    private String provinceName;

    @JsonProperty("pCompetentDepartment")
    private String pCompetentDepartment;

    private String year;

    private String month;

    @JsonProperty("pCompetentDepartmentName")
    private String pCompetentDepartmentName;

    private String newsunit;

    private String conunit;
}
