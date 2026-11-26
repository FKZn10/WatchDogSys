package com.jpmsoft.projectProducer.pojo.request.bim;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: 沈金安 Shen Jinan
 * @Date: 2024/5/29 14:10
 * @Description:
 * @Version: 1.0
 */
@Data
public class RiskCategoryHiddenCountRequest implements Serializable {

    private static final long serialVersionUID = -3058515334932251904L;

    private List<String> riskEvaluateIds;

    private String startTime;

    private String endTime;

    private String rectifyStatus;

    private String projectId;


}
