package com.jpmsoft.projectConsumer.pojo.dto.qualitySafety;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;


@Data
public class QsPTSpotCheckDto extends QsPlanTaskDto {

    //任务表id
    private String ptId;
    /**
     * 项目ID
     */
    private String pId;
    /**
     * 合同ID
     */
    private String conId;
    //备注
    private String qscRemark;

    private String teId;

    //项目名称
    private String pName;

    //编号
    private String ptCode;

    //检查时间
    private List<String> ptEvaluationTimes;

    //抽查数量
    private Integer checkNum;

    private String createUid;

    private String createUname;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;


    //抽查清单
    private List<QsPTSpotCheckRecordDto> qsPTSpotCheckRecordList;

}
