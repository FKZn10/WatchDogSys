package com.jpmsoft.projectProducer.pojo.dto.qualitySafety;

import lombok.Data;

import java.util.Date;

@Data
public class QsPTSpotCheckRecordDto {

    private String id;

    //抽查表id
    private String qsscId;

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

    //整改后照片
    private String rectifyPic;

    //实际整改人（文本输入）
    private String rectifyRealUname;

    /**
     * 整改回复
     */
    private String rectifyReply;

    //备注
    private String qscrRemark;

    //创建人
    private String createUid;

    //创建人姓名
    private String createUname;

    //创建时间
    private Date createTime;

}
