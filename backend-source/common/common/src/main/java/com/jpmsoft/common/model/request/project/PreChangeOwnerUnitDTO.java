package com.jpmsoft.common.model.request.project;

import lombok.Data;

/**
 * @author tianhelin
 * @date 2025/05/13
 */
@Data
public class PreChangeOwnerUnitDTO {
    /**
     * 主键
     */
    private String id;

    /**
     * 序号
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
     * 项目负责人名称
     */
    private String projectMangerName;

    /**
     * 项目分管领导
     */
    private String projectLeader;

    /**
     * 项目分管领导名称
     */
    private String projectLeaderName;

    /**
     * 备注
     */
    private String remark;
}
