package com.jpmsoft.projectProducer.pojo.entity.fund;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("Group_Fund_Plan_Summary")
public class GroupFundPlanSummary extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 5540011972892601733L;
    private String id;
    private Integer gfpsMonth;
    private Integer gfpsYear;
    private String gfpsTitle;
    // Gfps_Type
    private Integer gfpsType;
    // return_Type 类型 0、需要审批 1、无需审批
    private Integer returnType;
    private String gfpsRemark;
    private String gfpsProjectCompetentDepartmentId;
    private String groupId;
    private String groupMainType;
    @TableLogic
    private Integer delFlag;
}
