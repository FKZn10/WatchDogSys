package com.jpmsoft.common.model.response.proCp;

import lombok.Data;

import java.util.Date;

/**
 * 项目建设程序库-风险点防控措施关联表
 */
@Data
public class ProjectCpLibraryRiskResponse {
    private String id;
    private String cplId;
    private String rpmId;

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
    private Integer relationCount;
}