package com.jpmsoft.projectProducer.pojo.vo.design;

import com.jpmsoft.common.model.ProjectCommonRes;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 桩基检测
 * Created By:李文涛 aaron
 * Created Time:2022/6/22 13:55
 */
@Data
public class DesignPileVo extends ProjectCommonRes implements Serializable {

    private static final long serialVersionUID = -7995760833164025973L;

    private String dpId;
    private String conId;
    private String secId;
    private String dpSecName;
    private String dpType;
    private String dpPm;
    private Date dpStartTime;
    private Date dpEndTime;
    private String dpContent;
    private Integer state;
    private Date stateTime;
    private String dpCreateUid;
    private String dpCreateUname;
    private Date dpCreateTime;

    private String secName;
    private String conName;
    private String conCode;
}
