package com.jpmsoft.projectProducer.pojo.vo.qualitySafety;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class QsPTSpotCheckVo extends QsPlanTaskVo{

    private String id;
    private Integer state;
    private Date stateTime;
    private String createUid;
    private String createUname;
    private Date createTime;
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

    //抽查清单
    private List<QsPTSpotCheckRecordVo> qsPTSpotCheckRecordList;

}
