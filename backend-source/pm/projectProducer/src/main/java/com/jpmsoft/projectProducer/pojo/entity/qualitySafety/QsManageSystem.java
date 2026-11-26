package com.jpmsoft.projectProducer.pojo.entity.qualitySafety;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 质量管理体系
 */
@Data
@TableName("Qs_Manage_System")
public class QsManageSystem {

    private String id;

    //项目id
    private String pId;

    //项目管理单位
    private String projectManagementUnit;

    //项目建设模式
    private String projectBuildModel;

    //质量管理体系
    private String manageSystemDoc;

    //质量管理方案
    private String manageProgrammeDoc;

    //质量管理计划
    private String managePlanDoc;

    //备注
    private String remark;

    /**
     * 创建人id
     */
    private String createUid;

    /**
     * 创建人名称
     */
    private String createUname;

    @TableField(value = "Create_Time",fill = FieldFill.INSERT)
    private Date createTime;

    private Integer state;

    private Date stateTime;

    private String updateUid;

    @TableField(value = "Update_Time",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    private String teId;


}
