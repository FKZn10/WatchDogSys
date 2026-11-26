package com.jpmsoft.common.model.request.Oa;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaProjectReportModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 工作报告（日报、周报、月报、季报、年报）
 */
@Data
public class OaWorkReportRequestZhgd implements Serializable {

    private String wrDName;
    private String wrId;
    private String pId;
    private String secId;
    private String conId;
    private String wrDId;
    private String wrCId;
    private String wrCode;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date wrDate;
    private Date wrStartTime;
    private Date wrEndTime;
    private String wrTitle;
    private String wrContent;
    private String wrDoc;
    private String wrCreateUid;
    private String wrCreateUname;
    private Date wrCreateTime;
    private String wrType;
    private Date wrStateTime;
    private Integer wrState;
    private String wrProjectOverview;
    private String wrProjectSchedule;
    private Integer wrYear;
    private Integer wrQuarter;
    private Integer wrMonth;
    private String wrWeather;
    private String wrWindDirection;
    private Float wrWindMax;
    private Float wrWindMin;
    private Float wrWind;
    private Float wrTemperatureHigh;
    private Float wrTemperatureLow;
    private String wrContentQs;
    private String wrContentConstruct;
    private String wrContentOther;
    private String wrContractorId;

    private String pName;
    private String conName;
    private String userDid;
    private List<String> wrDates;
    private List<OaProjectReportModel> oaProReports;
}
