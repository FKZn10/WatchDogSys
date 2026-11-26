package com.jpmsoft.gmgws.pojo.vo;

import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.util.Date;

@Data
public class PaymentStatisticsFileVo  extends BaseFileRequest {

    @ProcessFields(type = ProcessFieldEnum.id)
    public String id;

    @ProcessFields(type = ProcessFieldEnum.createUid)
    private String createUid;

    @ProcessFields(type = ProcessFieldEnum.createUname)
    private String createUname;

    @ProcessFields(type = ProcessFieldEnum.createTime)
    private Date createTime;


    @ProcessFields(type = ProcessFieldEnum.state)
    private Integer state;

    @ProcessFields(type = ProcessFieldEnum.stateTime)
    private Date stateTime;


}
