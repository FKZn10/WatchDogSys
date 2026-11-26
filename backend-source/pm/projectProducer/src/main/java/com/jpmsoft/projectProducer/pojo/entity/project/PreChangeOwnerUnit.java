package com.jpmsoft.projectProducer.pojo.entity.project;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author tianhelin
 * @date 2025/05/13
 */
@Data
@TableName("pre_change_owner_unit")
public class PreChangeOwnerUnit {
    /**
     * 主键
     */
    private String id;

    /**
     * 项目储备变更主表ID（pre_project_reserve_change）
     */
    private String pprcId;

    /**
     * 序号
     */
    private Integer orderNumber;

    /**
     * 业主单位
     */
    private String ownerUnit;

    /**
     * 项目负责人
     */
    private String projectManger;

    /**
     * 项目分管领导
     */
    private String projectLeader;

    /**
     * 备注
     */
    private String remark;
}