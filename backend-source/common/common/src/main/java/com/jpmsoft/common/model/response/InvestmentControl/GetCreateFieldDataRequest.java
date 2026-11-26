package com.jpmsoft.common.model.response.InvestmentControl;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: 沈金安 Shen Jinan
 * @Date: 2024/4/1 15:38
 * @Description:
 * @Version: 1.0
 */
@Data
public class GetCreateFieldDataRequest implements Serializable {

    private static final long serialVersionUID = 4057506543163734727L;

    private String projectId;

    private String icmReportingMonth;

    private String icmReportingYear;
}
