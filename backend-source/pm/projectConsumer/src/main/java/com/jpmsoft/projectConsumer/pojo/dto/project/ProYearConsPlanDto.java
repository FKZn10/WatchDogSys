package com.jpmsoft.projectConsumer.pojo.dto.project;

import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/11/9 15:12
 */
@Data
public class ProYearConsPlanDto extends BaseFileDto {
    private static final long serialVersionUID = 7022986437029516320L;
    private Date ycpStartTime;
    private Date ycpEndTime;
    private String ycpRemark;
    private List<String> ycpStartTimes;
    private List<String> ycpEndTimes;
    private List<ProYearPlanSelectDto> proYearPlanSelects;
}
