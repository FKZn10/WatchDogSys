package com.jpmsoft.projectConsumer.pojo.dto.qualitySafety;

import lombok.Data;

import java.util.Date;

@Data
public class QsPTSpotCheckRecordDto {

    private String id;

    //任务记录表id
    private String qsrId;

    //整改状态
    private String qsrRectifyStatus;

    //要求整改时间
    private Date rectifyRequireTime;

    //整改完成时间
    private Date rectifyFinishTime;

    //新的整改状态
    private String newQsrRectifyStatus;

    //备注
    private String qscrRemark;

    //创建人
    private String createUid;

    //创建人姓名
    private String createUname;

    //创建时间
    private Date createTime;

}
