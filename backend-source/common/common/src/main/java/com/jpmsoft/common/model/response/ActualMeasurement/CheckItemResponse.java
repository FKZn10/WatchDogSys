package com.jpmsoft.common.model.response.ActualMeasurement;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@Data
public class CheckItemResponse {

    private String ciId;
    private String createUid;
    private String createUname;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    private String ciName;
    private String ciCode;
    private String ciType;
    private String ciTool;
    private String ciExpection;
    private String ciComputeExpection;
    private String ciIndicator;
    private String ciDescribe;
    private String ciMethodData;
    private String ciNodeType;
    private String ciParent;

    private Integer ciOrder;
    private List<CheckItemResponse> childNodes;
    private List<TaskDistributionCheckModel> taskDistributionCheck;
    private String ciFile;
    private String ciFileIds;

    private String dynamicId;

    private String dynamicName;

    private Integer isUseDynamic;
}