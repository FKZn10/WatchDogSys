package com.jpmsoft.common.model.response.ActualMeasurement;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class DrawingManagementCatalogueResponse {

    private String dmId;

    @JsonProperty("pId")
    private String pId;

    @JsonProperty("pName")
    private String pName;

    private String dmRegionName;

    private String dmParentId;

    private Integer dmOrder;

    private String dmCode;

    private String createUid;

    private String createUname;

    private Date createTime;

    // 爆点问题统计个数
    private Integer questionSum;

    private String dmDrawing;

    private String hasDrawing;

    // 爆点问题统计个数
    private Integer hfHgCount = 0;

    // 未测量的个数
    private Integer measuredCount = 0;


    private List<DrawingManagementCatalogueResponse> childNodes;
}
