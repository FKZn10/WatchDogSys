package com.jpmsoft.projectConsumer.pojo.dto.materialsEquipment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;

import java.util.List;

/**
 * 核价单
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/4 14:27
 */
@Data
public class MePriceListDto extends BaseFileDto {

    private static final long serialVersionUID = -3440360224409011124L;

    @JsonProperty("pId")
    private String pId;
    private String conId;
    private String plCode;
    private String plApplicant;
    private String plPm;
    private String plReason;
    private String plRemark;

    @JsonProperty("pName")
    private String pName;
    private String conName;
    private List<MePriceListDtlDto> mePriceListDtls;

}
