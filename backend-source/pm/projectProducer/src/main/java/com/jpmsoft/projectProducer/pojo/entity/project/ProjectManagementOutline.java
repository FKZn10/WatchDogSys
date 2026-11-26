package com.jpmsoft.projectProducer.pojo.entity.project;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("Project_Management_Outline")
public class ProjectManagementOutline extends BaseEntity implements Serializable {
    @ProcessFields(type = ProcessFieldEnum.id)
    private String id;
    private String projectId;
    private String title;
    private String urgencyLevel;
    private String preparedBy;
    private String preparedName;
    private String projectScale;
    private String supportingDocument;
    private String reviewOpinionForm;
    private String projectUndertake;
    private String projectUndertakeName;
    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    /**
     * 更新人
     */
    private String updateBy;
    /**
     * 删除状态：1正常 2删除
     */
    private Integer delFlag;
    /**
     * 状态：1 有效，2 禁用
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;
    /**
     * 建设模式
     */
    private String pBuildModel;
}
