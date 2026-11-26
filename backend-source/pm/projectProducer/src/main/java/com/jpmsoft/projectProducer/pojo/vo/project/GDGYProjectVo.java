package com.jpmsoft.projectProducer.pojo.vo.project;

import com.jpmsoft.common.model.response.Project.PreProjectResponse;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class GDGYProjectVo {
    private List<PreProjectResponse> data;

    private BigDecimal totalAmount;
}
