package com.jpmsoft.common.model.requestAndResponse.JpmOa.fund;

import com.jpmsoft.common.base.BaseFileDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class SalaryGrantInfoDto extends BaseFileDto {
    private static final long serialVersionUID = -8120463539825513258L;
    private String id;
    private Integer year;
    private Integer month;
    private String title;
    private BigDecimal salaryGrantInfoTotalSalary;
    private String createUname;
    private String createUid;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    private List<SalaryGrantInfoDetailDto> detailDtoList;
}
