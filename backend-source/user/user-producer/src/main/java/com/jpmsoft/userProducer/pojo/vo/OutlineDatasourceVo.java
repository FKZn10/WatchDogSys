package com.jpmsoft.userProducer.pojo.vo;

import com.jpmsoft.common.model.response.projectOutline.OutlineDatasourceTempResponse;
import com.jpmsoft.userProducer.pojo.dto.OutlineDatasourceDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class OutlineDatasourceVo extends OutlineDatasourceDto {
    private List<OutlineDatasourceTempResponse> datasourceTempList;
}
