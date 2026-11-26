package com.jpmsoft.projectProducer.pojo.entity.project;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 省试点项目
 *
 * <p>与项目申报 project_register_info 连动：relatedCityProjectId 记录所关联的项目申报主键，
 * 一个项目申报只能被一个试点项目选择（唯一性由代码保证，不建数据库唯一约束）。</p>
 *
 * @author whjp
 * @since 2026-09-16
 */
@Data
@TableName("prov_pilot_project")
public class ProvPilotProject implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键 id（雪花）
     */
    private String id;

    /**
     * 所属年度（即年份）
     */
    private Integer annualYear;

    /**
     * 批次
     */
    private String batch;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 项目类型（非字典）：房屋建筑=01、装饰装修=02、市政工程=03
     */
    private String projectType;

    /**
     * 项目编号（沿用原有编号生成规则自动生成）
     */
    private String projectCode;

    /**
     * 所在区（原样存储，不用字典）
     */
    private String district;

    /**
     * 申报单位
     */
    private String declareUnit;

    /**
     * 关联市项目名称（对应 project_register_info.name）
     */
    private String relatedCityProjectName;

    /**
     * 关联市项目 id（对应 project_register_info.id，一对一）
     */
    private String relatedCityProjectId;

    /**
     * 预留文件 id（同一批导入的记录共用同一个值；前端不传，后端导入成功后生成并统一刷新附件状态）
     */
    private String fileId;

    /**
     * 创建人 id
     */
    private String createUid;

    /**
     * 创建人名称
     */
    private String createUname;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 状态（导入默认 0）
     */
    private Integer state;

    /**
     * 状态时间（导入与创建时间一致）
     */
    private Date stateTime;

    /**
     * 修改人 id
     */
    private String updateUid;

    /**
     * 修改人名称
     */
    private String updateUname;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 租户 id（与其它主表一致，供租户隔离查询使用）
     */
    private String teId;
}