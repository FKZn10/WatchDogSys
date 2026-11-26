package com.jpmsoft.projectProducer.pojo.entity.project;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("pp_owner_unit")
public class PpOwnerUnit {
    /**
     * 主键
     */
    private String id;

    /**
     * 项目id（PreProject）
     */
    private String pId;

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