package com.jpmsoft.projectConsumer.pojo.dto.fund;

import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;

import java.util.List;

@Data
public class GroupFundPlanSummaryDto extends BaseFileDto {
    private static final long serialVersionUID = -864746749043656537L;
    private String id;
    private Integer gfpsMonth;
    private Integer gfpsYear;
    private String gfpsTitle;
    //Gfps_Type 类型 1、成员 2、集团
    private Integer gfpsType;
    private String gfpsRemark;
    private String gfpsProjectCompetentDepartmentId;
    private List<GroupFundPlanSummaryDetailDto> detailDtos;
    private String groupId;
    private String groupMainType;
    private Integer delFlag;

    /**
     * 操作类型
     */
    private String operationalType;

    private List<String> departmentIds;
}
