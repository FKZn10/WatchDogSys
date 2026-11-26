package com.jpmsoft.projectProducer.pojo.dto.fund;

import lombok.Data;

/***
 * 工程用款申请文号占用明细表Dto
 */
@Data
public class EngineeringFundApplicationFundNumberDetailDto {
    private String id;
    private String efaId;
    private String fnId;
    private Double efaOccupancyAmount;
}
