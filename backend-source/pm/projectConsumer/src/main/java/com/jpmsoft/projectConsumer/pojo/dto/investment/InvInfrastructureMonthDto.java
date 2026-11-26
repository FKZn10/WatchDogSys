package com.jpmsoft.projectConsumer.pojo.dto.investment;

import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;

import java.util.List;

/**
 * 基建投资月报
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/5 9:00
 */
@Data
public class InvInfrastructureMonthDto extends BaseFileDto {

    private static final long serialVersionUID = 5182873635794647136L;

    private String imYear;
    private String imMon;
    private String imRemark;

    private List<InvInfrastructureMonthDtlDto> invInfrastructureMonthDtls;
}
