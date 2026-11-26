package com.jpmsoft.common.model.request.ActualMeasurement;



import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class DrawingManagementCatalogueRequest extends BaseFileRequest {

    private String dmId;


    private String dmRegionName;

    private String dmParentId;

    private Integer dmOrder;

    private String dmCode;

    private String createUid;

    private String createUname;

    private Date createTime;

    // 指定已被分配任务的用户id
    private String userTaskId;

    // 图纸文件
    private String dmDrawing;

    private String dmDrawingIds;

    private String hasDrawing;

}
