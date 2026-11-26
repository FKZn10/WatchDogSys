package com.jpmsoft.common.model.request.ActualMeasurement;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.util.Date;


@Data
public class DrawingItemRequest  extends BaseFileRequest {


    private String diId;

    @JsonProperty("pId")
    private String pId;

    private String diRegionGroup;

    private String diRegionName;

    private String diRegionCode;

    private String diDrawing;
    private String diDrawingIds;

    private Integer diOrder;

    private String createUid;

    private String createUname;

    private Date createTime;

    private Integer state;

    private Date stateTime;

    // 任务分配的人员id
    private String taskDistributionUserId;
}