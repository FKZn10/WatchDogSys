package com.jpmsoft.common.model.response.Maintenance;

import lombok.Data;

import java.util.Date;

@Data
public class MaintenanceContentResponse {
    private String id;
    private String createUid;
    private String createUname;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    private String mContent;
    private String mPicture;
    private String mrId;
    private Date startTime;
    private Date endTime;
    private String endPicture;
}
