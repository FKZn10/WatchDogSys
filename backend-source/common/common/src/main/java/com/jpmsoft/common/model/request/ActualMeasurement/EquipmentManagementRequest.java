package com.jpmsoft.common.model.request.ActualMeasurement;



import com.jpmsoft.common.model.BasePageEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class EquipmentManagementRequest extends BasePageEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    private String emId;

    private String emName;

    private String emBlueName;

    private String emAppNotifyServiceCode;

    private String emAppNotifyCharacCode;

    private String emMiniNotifyServiceCode;

    private String emMiniNotifyCharacCode;

    private Integer emDataSendTimes;

    private String emDataType;

    private String emQuality;

    private String emSampling;

    private String emReply;

    private String emAppWriteServiceCode;

    private String emAppWriteCharacCode;

    private String emMiniWriteServiceCode;

    private String emMiniWriteCharacCode;

    private String createUid;

    private String createUname;

    private Date createTime;


    private Integer state;

    private Date stateTime;
}
