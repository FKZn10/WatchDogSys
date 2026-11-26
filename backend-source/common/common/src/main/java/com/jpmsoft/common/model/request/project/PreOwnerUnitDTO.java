package com.jpmsoft.common.model.request.project;

import lombok.Data;

import java.io.Serializable;

@Data
public class PreOwnerUnitDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 排序
     */
    private Integer orderNumber;

    /**
     * 业主单位
     */
    private String ownerUnit;

    /**
     * 业主单位名称
     */
    private String ownerUnitName;

    /**
     * 项目负责人
     */
    private String projectManger;

    /**
     * 项目负责人名
     */
    private String projectMangerName;

    /**
     * 项目分管领导
     */
    private String projectLeader;

    /**
     * 项目分管领导名
     */
    private String projectLeaderName;

    /**
     * 备注
     */
    private String remark;
}
