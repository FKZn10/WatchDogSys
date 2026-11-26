package com.jpmsoft.projectConsumer.request.qs;

import com.jpmsoft.common.model.BasePageEntity;
import lombok.Data;

import java.util.Date;

@Data
public class QsTypeRecordRequest extends BasePageEntity {
    //父级id
    private String qstParentId;
    private Date startTime;
    private Date endTime;
    private String ptType;
}
