package com.jpmsoft.projectConsumer.pojo.dto.riskWarn;

import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/3/29 11:29
 */
@Data

public class RiskPreventionMeasureDTO extends BaseModeRequest implements Serializable {

    private static final long serialVersionUID = 3882010801313736052L;

    private String id;
    private String createUid;
    private String createUname;
    private Date createTime;
    private String rpmParentId;
    private String rpmType;
    private String rpmLevel;
    private String rpmPoints;
    private String rpmMeasure;
    private String rpmInvolved;
    private String rpmDep;
    private String rpmContent;
    private Integer rpmSort;
    private String rpmIcon;
    private String rpmIconColor;
    private String rpmStatus;
    private List<RiskPreventionMeasureDTO> childNodes;
}
