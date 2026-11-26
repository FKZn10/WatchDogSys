package com.jpmsoft.common.model.response.Oa;

import lombok.Data;

@Data
public class OaWorkReportStandingbook {

    private String projectName;

    private String projectId;

    private String functionCode;

    private String functionName;

    private String alreadySubmissions;

    private String allSubmissions;

    private String neverSubmissions;

    /**
     *当前时间到截至时间内总数
     */
    private String allSubmitNowTime;

    /**
     *当前时间到截至时间内总未提交数
     */
    private String neverSubmitNowTime;
}
