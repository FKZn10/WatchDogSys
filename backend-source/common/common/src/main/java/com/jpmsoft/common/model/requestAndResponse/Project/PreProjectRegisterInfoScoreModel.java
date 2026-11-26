package com.jpmsoft.common.model.requestAndResponse.Project;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PreProjectRegisterInfoScoreModel {
    private String id;
    private String pId;
    private String zsrId;
    private String fileId;
    private BigDecimal score;
    private String fileName;
    private String percentage;
}
