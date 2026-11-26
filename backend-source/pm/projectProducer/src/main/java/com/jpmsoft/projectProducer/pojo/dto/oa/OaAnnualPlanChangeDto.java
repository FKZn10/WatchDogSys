package com.jpmsoft.projectProducer.pojo.dto.oa;

import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;

import java.util.List;

/**
 * 年度计划变更
 *
 * @Author: 李文涛 aaron
 * @date: 2022/12/2 15:00
 */
@Data
public class OaAnnualPlanChangeDto extends BaseFileDto {

    private static final long serialVersionUID = 7679990337011754389L;
    private String apId;
    private String apTitle;
    private String apYear;
    private String apRemark;
    private List<OaAnnualPlanChangeDtlDto> oaAnnualPlanChangeDtls;
}
