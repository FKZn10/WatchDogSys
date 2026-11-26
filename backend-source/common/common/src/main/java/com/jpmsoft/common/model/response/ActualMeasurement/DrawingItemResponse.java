package com.jpmsoft.common.model.response.ActualMeasurement;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BasePageEntity;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@Data
public class DrawingItemResponse {


    private String diId;

    @JsonProperty("pId")
    private String pId;

    @JsonProperty("pName")
    private String pName;

    private String diRegionGroup;

    private String diRegionName;
    private String dmParentRegionName;

    private String diRegionCode;

    private String diDrawing;

    private Integer diOrder;

    private String createUid;

    private String createUname;

    private Date createTime;

    private Integer state;

    private Date stateTime;

    List<TaskDistributionCheckModel> taskDistributionCheckModels;
}