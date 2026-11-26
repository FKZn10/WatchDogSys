package com.jpmsoft.projectProducer.pojo.dto.oa;

import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;

import java.util.List;

/**
 * 年度计划
 *
 * @Author: 李文涛 aaron
 * @date: 2022/12/2 15:00
 */
@Data
public class OaAnnualPlanDto extends BaseFileDto {
    private static final long serialVersionUID = 781665696805262032L;

    private String apTitle;
    private String apYear;
    private String apRemark;
    private String apdCheckType;
    private List<OaAnnualPlanDtlDto> oaAnnualPlanDtls;
}
