package com.jpmsoft.common.model.request.ActualMeasurement;

import com.jpmsoft.common.model.BasePageEntity;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class CheckItemRequest extends BasePageEntity {
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
    private String ciFile;
    private String ciFileIds;

    private Integer isUseDynamic;

    private String dynamicId;

}
